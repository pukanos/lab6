package server.commands;

import common.classes.Movie;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import server.utility.ResponsePrinter;

import java.util.LinkedList;
/**
 * Класс команды, отвечающей за вывод элементов в порядке возрастания.
 */
public class PrintAscendingCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    /**
     * Создает команду вывода элементов в порядке возрастания.
     * @param collectionManager утилита для работы с коллекцией
     */
    public PrintAscendingCommand(CollectionManager collectionManager){
        super("print_ascending", "", "Вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду.
     * Получает список фильмов и сортирует его.
     * Выводит каждый элемент нового списка.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject) {
        try {
            if (!argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            if (collectionManager.getCollectionSize() == 0) throw new CollectionIsEmptyException();
            ResponsePrinter.appendln("Вывод элементов в порядке возрастания");
            LinkedList<Movie> movies = collectionManager.getMovieCollection();
            movies.sort(Movie::compareTo);
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
