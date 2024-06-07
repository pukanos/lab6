package server.commands;

import common.classes.Movie;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import server.utility.ResponsePrinter;

import java.util.LinkedList;
/**
 * Класс команды, отвечающей за вывод коллекции.
 */
public class ShowCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    /**
     * Создает команду вывода коллекции.
     * @param collectionManager утилита для работы с коллекцией
     */
    public ShowCommand(CollectionManager collectionManager){
        super("show", "", "Вывести все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду.
     * Получает коллекцию фильмов.
     * Если она не пуста, проходит по каждому ее элементу и выводит его.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject){
        try {
            if (!argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            LinkedList<Movie> movies = collectionManager.getMovieCollection();
            if (movies.isEmpty()) throw new CollectionIsEmptyException();
            ResponsePrinter.appendln("Элементы коллекции:");

            movies.forEach(ResponsePrinter::appendln);

            return true;
        }
        catch (WrongNumberOfArgumentsException e){
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 0 аргументов");
            return false;
        }
        catch (CollectionIsEmptyException e){
            ResponsePrinter.appendln("Коллекция пуста!");
            return true;
        }
    }
}
