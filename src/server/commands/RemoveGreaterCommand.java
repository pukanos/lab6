package server.commands;

import common.classes.Movie;
import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import server.utility.ResponsePrinter;

import java.util.NoSuchElementException;
/**
 * Класс команды, отвечающей за удаление элементов больше данного.
 */
public class RemoveGreaterCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    /**
     * Создает команду удаления элементов больше данного.
     * @param collectionManager утилита для работы с коллекцией.
     */
    public RemoveGreaterCommand(CollectionManager collectionManager){
        super("remove_greater", "{element}", "Удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду.
     * Удаляет все элементы коллекции больше данного.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject){
        try{
        if (argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
        Movie movieToCompare = collectionManager.getByName(argument); // <-- commandObject no kak?
        if (movieToCompare == null) throw new NoSuchElementException();
        collectionManager.removeGreater(movieToCompare);
        ResponsePrinter.appendln("Элементы удалены!");
        return true;
        } catch (WrongNumberOfArgumentsException e){
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 1 значение - элемент коллекции");
        } catch (NoSuchElementException e){
            ResponsePrinter.appendError("Фильма с таким названием нет в коллекции!");
        }
        return false;
    }
}
