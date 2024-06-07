package server.commands;

import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.ResponsePrinter;

/**
 * Класс команды, отвечающей за вывод справки.
 */
public class HelpCommand extends AbstractCommand{
    /**
     * Создает команду справки.
     */
    public HelpCommand() {
        super("help", "", "Вывести справку по доступным командам");
    }
    /**
     * Выполняет команду.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject){
        try {
            if (!argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            return true;
        } catch (WrongNumberOfArgumentsException exception) {
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 0 аргументов!");
        }
        return false;
    }
}
