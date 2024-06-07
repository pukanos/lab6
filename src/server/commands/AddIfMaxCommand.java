package server.commands;

import common.classes.Movie;
import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import client.utility.MovieCreator;
import server.utility.ResponsePrinter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
/**
 * Класс команды, отвечающий за добавление фильма в коллекцию, если он больше любого другого элемента этой коллекции
 */
public class AddIfMaxCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final MovieCreator movieCreator;
    /**
     * Создает команду добавления элемента, если он больше любого другого элемента коллекции
     * @param collectionManager утилита для работы с коллекцией
     * @param movieCreator утилита для создания фильма
     */
    public AddIfMaxCommand(CollectionManager collectionManager, MovieCreator movieCreator){
        super("add_if_max", "{element}", "Добавить новый элемент в коллекцию, если его значение" +
                "превышает значение наибольшего элемента коллекции");
        this.collectionManager = collectionManager;
        this.movieCreator = movieCreator;
    }
    /**
     * Выполняет команду добавления фильма в коллекцию.
     * Создает пустой фильм, с помощью movieCreator создает поля фильма, с помощью collectionManager добавляет фильм в коллекцию.
     * Если фильм не превосходит все другие фильмы коллекции, то он добавляется в коллекцию.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректна, false - иначе
     */
    @Override
    public boolean execute(String argument, Object commandObject) {
        try {
            if (!argument.isEmpty() || commandObject == null) throw new WrongNumberOfArgumentsException();
//            Movie movieToAdd = new Movie();
            Movie movieToAdd = (Movie) commandObject;
//            movieCreator.createMovie(movieToAdd);
            LinkedList<Movie> movieCollection = collectionManager.getMovieCollection();
            Movie maxMovie = movieCollection.stream()
                    .max(Comparator.comparing(Movie::getName))
                    .orElseThrow(NoSuchElementException::new);
            if (movieCollection.isEmpty() || movieToAdd.compareTo(maxMovie) > 0){
                movieToAdd.setId(collectionManager.generateId());
                movieToAdd.updateCreationDate();
                ResponsePrinter.appendln("Элемент добавлен!");
                collectionManager.addToCollection(movieToAdd);
                return true;
            }
            ResponsePrinter.appendln("Элемент не добавлен: он меньше максимального!");
        }
        catch (WrongNumberOfArgumentsException e){
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 0 аргументов");
        }
//        catch (InvalidScriptInputException ignored) {}
        return false;
    }
}
