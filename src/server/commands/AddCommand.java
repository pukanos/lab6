package server.commands;

import common.classes.*;
import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import client.utility.MovieCreator;
import server.utility.ResponsePrinter;

/**
 * Класс команды, отвечающей за добавления фильма в коллекцию.
 */
public class AddCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final MovieCreator movieCreator; // нужно ли?

    /**
     * Создает команду добавления элемента.
     *
     * @param collectionManager утилита для работы с коллекцией
     * @param movieCreator      утилита для создания фильма
     */
    public AddCommand(CollectionManager collectionManager, MovieCreator movieCreator) {
        super("add", "{element}", "Добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.movieCreator = movieCreator;
    }

    /**
     * Выполняет команду добавления фильма в коллекцию.
     * Создает пустой фильм, с помощью movieCreator создает поля фильма, с помощью collectionManager добавляет фильм в коллекцию.
     *
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean execute(String argument, Object commandObject) {
        try {
            if (!argument.isEmpty() || commandObject == null) throw new WrongNumberOfArgumentsException();
            {
//                Movie movie = new Movie();
//                movieCreator.createMovie(movie);
                Movie movie = (Movie) commandObject;
                movie.setId(collectionManager.generateId());
                movie.updateCreationDate();
                collectionManager.addToCollection(movie);

                ResponsePrinter.appendln("Элемент добавлен в коллекцию!");
//                System.out.println("Элемент добавлен в коллекцию!");
                return true;
            }
        } catch (WrongNumberOfArgumentsException e) {

//            System.err.println("Команда " + getCommandName() + " принимает 0 аргументов");
            ResponsePrinter.appendError("Команда " + getCommandName() + " принимает 0 аргументов");
        }
//        catch (InvalidScriptInputException ignored) {}
            return false;
    }
}