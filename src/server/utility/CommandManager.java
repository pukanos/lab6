package server.utility;

import server.commands.*;
import common.exceptions.NoSuchCommandException;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * Класс, отвечающий за работу и хранение команд.
 */
public class CommandManager {
    int COMMAND_HISTORY_SIZE = 12;
    private final String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private final LinkedList<ICommand> commands;
    private static HelpCommand helpCommand;
    private static InfoCommand infoCommand;
    private static ShowCommand showCommand; //
    private static AddCommand addCommand;
    private static UpdateCommand updateCommand;
    private static RemoveByIdCommand removeByIdCommand;
    private static ClearCommand clearCommand;
    private static SaveCommand saveCommand;
    private static ExitCommand exitCommand;
    private static ExecuteScriptCommand executeScriptCommand;
    private static AddIfMaxCommand addIfMaxCommand;
    private static RemoveGreaterCommand removeGreaterCommand;
    private static HistoryCommand historyCommand;
    private static FilterLessThanDirectorCommand filterLessThanDirectorCommand; //
    private static PrintAscendingCommand printAscendingCommand; //
    private static PrintUniqueOscarsCountCommand printUniqueOscarsCountCommand; //

    private static CommandManager instance;
    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager(helpCommand, infoCommand, updateCommand,
                    addCommand, exitCommand, historyCommand,
                    removeByIdCommand, saveCommand, showCommand,
                    clearCommand, printAscendingCommand, printUniqueOscarsCountCommand,
                    filterLessThanDirectorCommand, removeGreaterCommand,
                    addIfMaxCommand, executeScriptCommand);
        }
        return instance;
    }

//    public String[] parseCommand(String command) {
//        return command.trim().split("\\s+");
//    }
//    public ICommand getCommand(String command) throws NoSuchCommandException {
//        if (!commands.contains(command)) throw new NoSuchCommandException();
//        return commands.get(commands.indexOf(command));
//    }

    /**
     * Возвращает список команд.
     * @return список команд
     */

    public LinkedList<ICommand> getCommands() {
        return commands;
    }
    /**
     * Возвращает массив последних использованных команд.
     * @return массив команд
     */
    public String[] getCommandHistory() {
        return commandHistory;
    }
    /**
     * Создает элемент класса CommandManager для работы с командами.
     * @param helpCommand команда вывода помощи
     * @param infoCommand команда вывода информации о коллекции
     * @param updateCommand команда обновления элемента
     * @param addCommand команда добавления элемента
     * @param exitCommand команда завершения работы
     * @param historyCommand команда вывода истории использованных команд
     * @param removeByIdCommand команда удаления элемента по ID
     * @param saveCommand команда сохранения коллекции
     * @param showCommand команда вывода всех элементов коллекции
     * @param clearCommand команда очищения коллекции
     * @param printAscendingCommand команда вывода элементов коллекции в порядке возрастания
     * @param printUniqueOscarsCountCommand команда вывода элементов, количество оскаров которых уникально
     * @param filterLessThanDirectorCommand команда вывода элементов, значение режиссера которых меньше чем, у заданного фильма
     * @param removeGreaterCommand команда удаления элементов больше заданного
     * @param addIfMaxCommand команда добавления элемента, если он больше любого другого элемента коллекции
     * @param executeScriptCommand команда выполнения скрипта из заданного файла
     */
    public CommandManager(HelpCommand helpCommand, InfoCommand infoCommand, UpdateCommand updateCommand,
                          AddCommand addCommand, ExitCommand exitCommand, HistoryCommand historyCommand,
                          RemoveByIdCommand removeByIdCommand, SaveCommand saveCommand,ShowCommand showCommand,
                          ClearCommand clearCommand, PrintAscendingCommand printAscendingCommand, PrintUniqueOscarsCountCommand printUniqueOscarsCountCommand,
                          FilterLessThanDirectorCommand filterLessThanDirectorCommand, RemoveGreaterCommand removeGreaterCommand,
                          AddIfMaxCommand addIfMaxCommand, ExecuteScriptCommand executeScriptCommand){ //добавить команды
        CommandManager.helpCommand = helpCommand;
        CommandManager.infoCommand = infoCommand;
        CommandManager.updateCommand = updateCommand;
        CommandManager.addCommand = addCommand;
        CommandManager.exitCommand = exitCommand;
        CommandManager.historyCommand = historyCommand;
        CommandManager.removeByIdCommand = removeByIdCommand;
        CommandManager.saveCommand = saveCommand;
        CommandManager.showCommand = showCommand;
        CommandManager.clearCommand = clearCommand;
        CommandManager.filterLessThanDirectorCommand = filterLessThanDirectorCommand;
        CommandManager.printAscendingCommand = printAscendingCommand;
        CommandManager.printUniqueOscarsCountCommand = printUniqueOscarsCountCommand;
        CommandManager.removeGreaterCommand = removeGreaterCommand;
        CommandManager.addIfMaxCommand = addIfMaxCommand;
        CommandManager.executeScriptCommand = executeScriptCommand;
        commands = new LinkedList<>(Arrays.asList(helpCommand, infoCommand, updateCommand, addCommand,
                exitCommand, historyCommand, removeByIdCommand, saveCommand, showCommand, clearCommand,
                filterLessThanDirectorCommand, printAscendingCommand, printUniqueOscarsCountCommand,
                removeGreaterCommand, addIfMaxCommand, executeScriptCommand));
    }
    /**
     * Добавляет заданную команду в историю использованных команд.
     * @param commandToStore команда, которую нужно добавить в историю
     */
    public void addToHistory(String commandToStore) {
        for (ICommand command : commands) {
            if (command.getCommandName().split(" ")[0].equals(commandToStore)) {
                IntStream.iterate(COMMAND_HISTORY_SIZE - 1, i -> i > 0, i -> i - 1)
                        .forEach(i -> commandHistory[i] = commandHistory[i - 1]);
                commandHistory[0] = commandToStore;
            }
        }
    }
    /**
     * Выполняется в том случае, когда введенная команда не существует.
     *
     * @param command       ненайденная команда
     * @param commandObject
     * @return false
     */
    public boolean noSuchCommand(String command, Object commandObject) {
//        System.out.println("Команда " + command + " была не найдена. Наберите 'help' для справки.");
        return false;
    }
    /**
     * Выводит справку по имеющимся командам.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean help(String argument, Object commandObject){
        if (helpCommand.execute(argument, commandObject)){
            for (ICommand command : commands){
                if (!(command.equals(saveCommand))) ResponsePrinter.appendln(command.toString());
            }
            return true;
        }return false;
    }
    /**
     * Выводит информацию о коллекции.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean info(String argument, Object commandObject){
        return infoCommand.execute(argument,commandObject);
    }
    /**
     * Выводит элементы коллекции.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean show(String argument, Object commandObject){
        return showCommand.execute(argument,commandObject);
    }
    /**
     * Добавляет элемент в коллекцию.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean add(String argument, Object commandObject){
        return addCommand.execute(argument,commandObject);
    }
    /**
     * Добавляет элемент в коллекцию, если он больше любого другого элемента коллекции.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean addIfMax(String argument, Object commandObject){
        return addIfMaxCommand.execute(argument,commandObject);
    }
    /**
     * Обновляет элемент коллекции.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean update(String argument, Object commandObject){
        return updateCommand.execute(argument,commandObject);
    }
    /**
     * Удаляет элемент с заданным ID.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean removeById(String argument, Object commandObject){
        return removeByIdCommand.execute(argument,commandObject);
    }
    /**
     * Очищает коллекцию.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean clear(String argument, Object commandObject){
        return clearCommand.execute(argument,commandObject);
    }
    /**
     * Сохраняет коллекцию в файл.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean save(String argument, Object commandObject){
        return saveCommand.execute(argument,commandObject);
    }
    /**
     * Завершает работу программы.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean exit(String argument, Object commandObject){
        return exitCommand.execute(argument,commandObject);
    }
    /**
     * Выполняет скрипт из данного файла.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean executeScriptCommand(String argument, Object commandObject){
        return executeScriptCommand.execute(argument,commandObject);
    }
    /**
     * Удаляет элементы больше данного.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean removeGreater(String argument, Object commandObject){
        return removeGreaterCommand.execute(argument,commandObject);
    }
    /**
     * Выводит массив использованных команд.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean history(String argument, Object commandObject){
        if (historyCommand.execute(argument,commandObject)){
            if (commandHistory.length == 0) {
                ResponsePrinter.appendln("Ни одной команды еще не было введено");
                return false;
            }
            else{
                ResponsePrinter.appendln("История использования команд:");
                // first -> last
                IntStream.iterate(commandHistory.length - 1, i -> i != 0, i -> i - 1)
                        .filter(i -> commandHistory[i] != null)
                        .mapToObj(i -> commandHistory[i])
                        .forEach(ResponsePrinter::appendln);
/*                for (int i = commandHistory.length - 1; i != 0; i--){ // first -> last
                    if (commandHistory[i] != null) System.out.println(commandHistory[i]);
                }*/
//                for (String i:commandHistory){ // last -> first
//                    if (!(i == null)) System.out.println(i);
//                }
                return true;
            }
        }
        return false;
    }
    /**
     * Выводит элементы коллекции в порядке возрастания.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean printAscending(String argument, Object commandObject){
        return printAscendingCommand.execute(argument,commandObject);
    }
    /**
     * Выводит элементы, количество оскаров которых уникально.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean printUniqueOscarsCount(String argument, Object commandObject){
        return printUniqueOscarsCountCommand.execute(argument,commandObject);
    }
    /**
     * Выводит элементы, значение режиссера которых меньше заданного.
     *
     * @param argument      аргумент команды
     * @param commandObject
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean filterLessThanDirector(String argument, Object commandObject){
        return filterLessThanDirectorCommand.execute(argument,commandObject);
    }
}
