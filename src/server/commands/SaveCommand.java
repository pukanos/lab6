package server.commands;

import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import server.utility.ResponsePrinter;

/**
 * Класс команды, отвечающей за сохранение коллекции в файл.
 */
public class SaveCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    /**
     * Создает команду сохранения коллекции.
     * @param collectionManager утилита для работы с коллекцией
     */
    public SaveCommand(CollectionManager collectionManager){
        super("save", "", "Сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду.
     * Сохраняет коллекцию.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject){
        try {
            if (!argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            collectionManager.saveCollection();
            return true;
        }
        catch (WrongNumberOfArgumentsException e){
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 0 аргументов");
            return false;
        }
    }
}
