package server.commands;

import common.classes.Movie;
import common.classes.Person;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import server.utility.ResponsePrinter;

import java.util.LinkedList;
import java.util.stream.Collectors;
/**
 * Класс команды, отвечающей за вывод элементов, значение режиссера которых меньше данного.
 */
public class FilterLessThanDirectorCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    /**
     * Создает команду вывода элементов, значение режиссера которых меньше данного.
     * @param collectionManager утилита для работы с коллекцией
     */
    public FilterLessThanDirectorCommand(CollectionManager collectionManager){ // поменять описание
        super("filter_less_than_director", "{element}", "Вывести элементы, значение поле director меньше заданного");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду.
     * Получает коллекцию, затем получает список режиссеров всех фильмов.
     * Проходит по списку режиссеров.
     * Если режиссер меньше заданного, выводит фильм.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject){
        try {
            if (argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            LinkedList<Movie> movies = collectionManager.getMovieCollection();
            LinkedList<Movie> moviesCopy = new LinkedList<>(movies);
            if (movies.isEmpty()) throw new CollectionIsEmptyException();
            LinkedList<String> moviesDirectors = movies.stream()
                    .map(Movie::getDirector)
                    .map(Person::getName)
                    .map(String::toLowerCase)
                    .collect(Collectors.toCollection(LinkedList::new));
            LinkedList<Integer> appearedIds = new LinkedList<>();
            String directorToCompare = argument.trim().toLowerCase();
            ResponsePrinter.appendln("Элементы коллекции, удовлетворяющие данному условию:");
            for (String director : moviesDirectors){
                if (director.compareTo(directorToCompare) < 0){
                    while (appearedIds.contains(movies.get(moviesDirectors.indexOf(director)).getId())) {
                        movies.removeFirstOccurrence(movies.get(moviesDirectors.indexOf(director)));
                    }
                    appearedIds.add(movies.get(moviesDirectors.indexOf(director)).getId());
                    ResponsePrinter.appendln(movies.get(moviesDirectors.indexOf(director)));
                }
            }
            collectionManager.setMovieCollection(moviesCopy);
            return true;
        }
        catch (WrongNumberOfArgumentsException e){
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 1 аргумент - режиссер фильма");
        }
        catch (CollectionIsEmptyException e){
            ResponsePrinter.appendError("Коллекция пуста!");
            return true;
        }
        return false;
    }
}
