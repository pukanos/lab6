package server;

import client.utility.MovieCreator;
import server.commands.*;
import common.Request;
import server.utility.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;

public class Server {
    Selector selector;
    private DatagramChannel datagramChannel;
    private RequestHandler requestHandler;
    private static final int PORT = 8888;

    private void run(String filePath) {
        try {
            selector = Selector.open();
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            datagramChannel.bind(new InetSocketAddress("localhost", PORT));
            datagramChannel.register(selector, SelectionKey.OP_READ);

            FileManager fileManager = new FileManager(filePath);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            MovieCreator movieCreator = new MovieCreator(new Scanner(System.in));
            CommandManager commandManager = new CommandManager(
                    new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new UpdateCommand(movieCreator, collectionManager),
                    new AddCommand(collectionManager, movieCreator),
                    new ExitCommand(),
                    new HistoryCommand(),
                    new RemoveByIdCommand(collectionManager),
                    new SaveCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new PrintAscendingCommand(collectionManager),
                    new PrintUniqueOscarsCountCommand(collectionManager),
                    new FilterLessThanDirectorCommand(collectionManager),
                    new RemoveGreaterCommand(collectionManager),
                    new AddIfMaxCommand(collectionManager, movieCreator),
                    new ExecuteScriptCommand()
            );
            requestHandler = new RequestHandler(commandManager);
            System.out.println("Начало работы сервера");

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // ввод с клиента
                if (selector.selectNow() > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            if (key.channel() instanceof DatagramChannel) {
                                receive((DatagramChannel) key.channel());
                            }
                        }
                    }
                }

                // ввод с консоли сервера
                if (System.in.available() > 0) {
                    handleConsoleInput(consoleReader);
                }
            }
        } catch (FileNotFoundException e) {
            ResponsePrinter.appendError("Файл не найден или отсутствуют права доступа к нему!");
        } catch (IOException e) {
            ResponsePrinter.appendError("Ошибка ввода/вывода!");
        }
    }

    private void receive(DatagramChannel datagramChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(100000);
        buffer.clear();
        SocketAddress socketAddress = datagramChannel.receive(buffer);
        buffer.flip();
        if (socketAddress != null) {
            Object object = Serialization.deserializeObject(buffer.array());
            Request request = (Request) object;
            String response = requestHandler.handle(request);
            datagramChannel.send(ByteBuffer.wrap(Serialization.serializeObject(response)), socketAddress);
//            System.out.println(socketAddress);
        }
    }

    private void handleConsoleInput(BufferedReader consoleReader){
        try {
            if (consoleReader.ready()) {
                String input = consoleReader.readLine().trim().toLowerCase();
                switch (input){
                    case "" -> {}
                    case "exit" ->{
                        System.out.println("Завершение работы сервера");
                        System.exit(0);
                    }
                    case "save" -> requestHandler.handle(new Request("save",""));
                    default -> System.out.println("Сервер принимает только две команды - save и exit");
                }
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Завершение работы сервера");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        String filePath = null;
        if (args.length == 0) {
            filePath = "data.csv";
        } else if (args.length > 1) {
            System.err.println("Введите 1 аргумент! Вы ввели: " + args.length);
            System.exit(1);
        } else {
            filePath = args[0];
        }
        server.run(filePath);
    }
}
