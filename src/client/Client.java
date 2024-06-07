package client;

import client.utility.UserInputHandler;
import common.Request;
import server.utility.Serialization;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private final static int PORT = 8888;
    private final static String HOST = "localhost";

    private final DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private final Selector selector;

    public Client() throws IOException {
        selector = Selector.open();
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
    }
    private void connect(String hostname, int port) throws IOException {
        try{
        socketAddress = new InetSocketAddress(hostname, port);
        datagramChannel.connect(socketAddress);
        System.out.println("Подключение к " + hostname + ":" + port + "...");
        } catch (StreamCorruptedException e){
            System.out.println("Невозможно подключиться к серверу!");
            System.exit(1);
        }
    }
    private Object receive() throws IOException{
        ByteBuffer receiveBuffer = ByteBuffer.wrap(new byte[10000]);
        socketAddress  = datagramChannel.receive(receiveBuffer);
        return Serialization.deserializeObject(receiveBuffer.array());
    }
    private void send(Object obj) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(Serialization.serializeObject(obj));
        datagramChannel.send(buffer, socketAddress);
    }

    public void run() throws IOException {
        try {
            Scanner scanner = new Scanner(System.in);
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            UserInputHandler inputHandler = new UserInputHandler(scanner);

            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        datagramChannel.register(selector, SelectionKey.OP_WRITE);
                        Object obj = receive();
                        String response = (String) obj;
                        if (response.equals("Завершение работы")) {
                            send(new Request("save", ""));
                            System.out.println("Сохранение коллекции");
                            System.out.println(response);
                            System.exit(0);
                        }
                        System.out.println(response);
                    }
                    if (key.isWritable()) {
                        datagramChannel.register(selector, SelectionKey.OP_READ);
                        if (inputHandler.scriptMode()){
                            scanner = new Scanner(inputHandler.getScriptStack().peek());
                        }
                        else {
                            scanner = new Scanner(System.in);
                        }
                        String[] command = (scanner.nextLine().trim() + " ").split(" ", 2);
                        command[1] = command[1].trim();
                        Request request = inputHandler.handle(command);
                        send(request);
                    }
                }
            }
        } catch (PortUnreachableException e) {
            System.out.println("Сервер недоступен!");
            System.exit(-1);
        } catch (NoSuchElementException e) {
            System.out.println("Завершение работы");
            send(new Request("save",""));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.connect(HOST,PORT);
            client.run();
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода!");
            System.exit(-1);
        }
    }
}