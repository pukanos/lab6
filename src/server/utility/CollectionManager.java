package server.utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import common.classes.Movie;

/**
 * Класс, отвечающий за работу коллекции.
 */
public class CollectionManager {
    private final FileManager fileManager;
    private LinkedList<Movie> movieCollection;
    private final LocalDateTime creationDate;
    private LocalDateTime lastUpdate;
    /**
     * Создает элемент класса CollectionManager и загружает коллекцию из соответствующего для fileManager файла.
     * @param fileManager для того чтобы collectionManager мог работать с файлом
     */
    public CollectionManager(FileManager fileManager){
        movieCollection = new LinkedList<>();
        creationDate = LocalDateTime.now();
        this.fileManager = fileManager;
        loadCollection();
    }
    /**
     * Возвращает размер коллекции.
     * @return размер коллекции
     */
    public int getCollectionSize(){
        return movieCollection.size();
    }
    /**
     * Возвращает коллекцию.
     * @return коллекцию
     */
    public LinkedList<Movie> getMovieCollection() {
        return movieCollection;
    }
    /**
     * Устанавливает данную коллекцию как ту, с которой будет работа.
     * @param movieCollection коллекция
     */
    public void setMovieCollection(LinkedList<Movie> movieCollection){
        this.movieCollection = movieCollection;
    }
    /**
     * Возвращает время создания коллекции.
     * @return время создания коллекции
     */
    public LocalDateTime getCreationDate(){
        return creationDate;
    }
    /**
     * Добавляет элемент в коллекцию.
     * @param movie фильм, который нужно добавить
     */
    public void addToCollection(Movie movie){
        movieCollection.add(movie);
    }
    /**
     * Удаляет элемент из коллекции.
     * @param movie фильм, который нужно удалить
     */
    public void removeFromCollection(Movie movie){
        movieCollection.remove(movie);
    }
    /**
     * Удаляет из коллекции элемент с заданным ID.
     * @param id ID элемента, который нужно удалить
     */
    public void removeById(Integer id){
        movieCollection.removeIf(movie -> movie.getId().equals(id));
    }
    /**
     * Удаляет из коллекции элементы, названия которых идут после названия данного по алфавитному порядку.
     * @param movie фильм, с которым будет идти сравнение
     */
    public void removeGreater(Movie movie){
        movieCollection.removeIf(movie1 -> movie1.compareTo(movie) > 0);
    }
    /**
     * Возвращает элемент коллекции с заданным ID.
     * @param id ID элемента, который нужно получить
     * @return элемент
     */
    public Movie getById(Integer id){
        for (Movie movie: movieCollection){
            if (movie.getId().equals(id)) return movie;
        }
        List<Movie> da = new ArrayList<>();

        movieCollection.stream().toList().getClass();
        return null;
    }
    /**
     * Возвращает элемент коллекции с заданным названием.
     * @param movieName название фильма
     * @return элемент
     */
    public Movie getByName(String movieName){
        for (Movie movie : movieCollection){
            if (movie.getName().equals(movieName)) return movie;
        }
        return null;
    }
    /**
     * Удаляет все элементы из коллекции.
     */
    public void clearCollection(){
        movieCollection.clear();
    }
    /**
     * Возвращает информацию о коллекции.
     * @return тип, дату создания, количество элементов коллекции
     */
    public String getCollectionInfo(){
        return ("тип: " + movieCollection.getClass() + ", дата создания: " + creationDate +
                ", количество элементов: " + movieCollection.size());
    }
    /**
     * Возвращает тип коллекции.
     * @return тип коллекции
     */
    public String getCollectionType(){
        return movieCollection.getClass().getName();
    }
    /**
     * Сохраняет коллекцию в файл.
     */
    public void saveCollection(){
        fileManager.writeCollection(movieCollection);
        updateTime();
    }
    /**
     * Загружает коллекцию из файла.
     */
    public void loadCollection(){
        movieCollection = fileManager.readCollection();
        lastUpdate = LocalDateTime.now();
    }
    /**
     * Возвращает время последнего обновления коллекции.
     * @return время последнего обновления
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /**
     * Обновляет время последнего обновления коллекции.
     */
    public void updateTime(){
        lastUpdate = LocalDateTime.now();
    }
    /**
     * Генерирует уникальный ID для элемента коллекции.
     * @return ID
     */
    public Integer generateId(){
        int id = movieCollection.stream()
                .mapToInt(Movie::getId)
//                .filter(movieId -> movieId >= 0)
                .max().orElse(0);
        return id + 1;
    }
}
