package server.commands;

import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.ResponsePrinter;

/**
 * Класс команды, отвечающей за завершение работы программы.
 */
public class ExitCommand extends AbstractCommand{
    /**
     * Создает команду завершения работы.
     */
    public ExitCommand(){
        super("exit", "",
                "Сохранить коллекцию и завершить работу программы");
    }
    /**
     * Завершает работу программы, если выполнена корректно
     * @param argument аргумент команды.
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject){
        try {
            if (!argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            ResponsePrinter.append("Завершение работы");
//            System.exit(0);

            return true;
        }
        catch (WrongNumberOfArgumentsException e){
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 0 аргументов"); // ???
            return false;
        }
    }
}
