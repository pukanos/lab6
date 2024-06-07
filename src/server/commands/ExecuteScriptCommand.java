package server.commands;

import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.ResponsePrinter;

/**
 * Класс команды, отвечающей за выполнение скрипта.
 */
public class ExecuteScriptCommand extends AbstractCommand{
    /**
     * Создает команду выполнения скрипта.
     */
    public ExecuteScriptCommand() {
        super("execute_script", "{file_name}", "Исполнить скрипт из указанного файла");
    }
    /**
     * Выполняет команду выполнения скрипта.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject) {
        try {
            if (argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
//            ResponsePrinter.appendln("Выполнение следующего скрипта: " + argument);
            return true;
        } catch (WrongNumberOfArgumentsException exception) {
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 1 аргумент - имя файла со скриптом");
//            System.err.println("Команда " + getCommandName() + " принимает 1 аргумент - имя файла со скриптом");
        }
        return false;
    }
}
