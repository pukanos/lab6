package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import server.utility.ResponsePrinter;

import java.util.NoSuchElementException;
/**
 * Класс команды, отвечающей за удаление элемента по его ID.
 */
public class RemoveByIdCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    /**
     * Создает команду удаления элемента по его ID.
     * @param collectionManager утилита для работы с коллекцией
     */
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "{id}", "Удалить элемент из коллекции по его ID");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду.
     * Принимает аргумент команды как ID.
     * Если в коллекции существует элемент с данным ID, удаляет его.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject) {
        try { // нет разницы по сравнению с 5 лабой
            if (argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            if (collectionManager.getCollectionSize() == 0) throw new CollectionIsEmptyException();
            Integer id = Integer.parseInt(argument.trim());
            if (collectionManager.getById(id) == null) throw new NoSuchElementException();
            collectionManager.removeById(id);
            ResponsePrinter.appendln("Элемент удален!");
            return true;
        }
        catch (WrongNumberOfArgumentsException e){
            ResponsePrinter.appendError("Команда " + getCommandName() + " должна принимать в качестве аргумента ID фильма");
            return false;
        }
        catch (CollectionIsEmptyException e) {
            ResponsePrinter.appendError("Коллекция пуста");
            return false;
        }
        catch (NoSuchElementException e){
            ResponsePrinter.appendError("Элемента с таким ID в коллекции нет");
            return false;
        }
    }
}
