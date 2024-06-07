package server.commands;

import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import server.utility.ResponsePrinter;

/**
 * Класс команды, отвечающей за очищение коллекции.
 */
public class ClearCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    /**
     * Создает команду очищения коллекции.
     * @param collectionManager утилита для работы с коллекцией.
     */
    public ClearCommand(CollectionManager collectionManager) {
        super("clear" ,"", "Очистить коллекцию");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду очищения коллекции. Удаляет все элементы коллекции.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject) {
        try {
            if (!argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            collectionManager.clearCollection();
            ResponsePrinter.appendln("Коллекция очищена!");
            return true;
        }
        catch (WrongNumberOfArgumentsException e){
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 0 аргументов");
            return false;
        }
    }
}
