package server.commands;

import common.exceptions.WrongNumberOfArgumentsException;
import server.utility.CollectionManager;
import server.utility.ResponsePrinter;

import java.time.LocalDateTime;
/**
 * Класс команды, отвечающей за вывод информации о коллекции.
 */
public class InfoCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    /**
     * Создает команду вывода информации о коллекции.
     * @param movieCollection утилита для работы с коллекцией
     */
    public InfoCommand(CollectionManager movieCollection) {
        super("info", "", "Вывести информацию о коллекции");
        this.collectionManager = movieCollection;
    }
    /**
     * Выполняет команду.
     * Выводит тип, количество элементов и время последнего обновления коллекции, если она не пуста.
     * @param argument аргумент команды
     * @return true, если команда выполнена корректно, false - иначе
     */
    public boolean execute(String argument, Object commandObject){
        try {
            if (!argument.isEmpty() || commandObject != null) throw new WrongNumberOfArgumentsException();
            LocalDateTime lastUpdate = collectionManager.getLastUpdate();
            String lastUpdateString = (lastUpdate == null) ? "Данная коллекция еще не была обновлена" :
                    lastUpdate.toLocalTime().toString() + " " + lastUpdate.toLocalDate().toString();
            ResponsePrinter.appendln("Информация о коллекции:");
            ResponsePrinter.appendln("Тип: " + collectionManager.getCollectionType());
            ResponsePrinter.appendln("Количество элементов: " + collectionManager.getCollectionSize());
            ResponsePrinter.appendln("Время последнего обновления: " + lastUpdateString);
        }
        catch (NullPointerException e){
            ResponsePrinter.appendError("Коллекция пуста!");
        } catch (WrongNumberOfArgumentsException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
