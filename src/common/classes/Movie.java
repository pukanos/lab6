package common.classes;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс фильма как элемента для последующего добавления в коллекцию
 */
public class Movie implements Comparable<Movie>, Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private MovieGenre genre; //Поле может быть null
    private MpaaRating mpaaRating; //Поле не может быть null
    private Person director; //Поле не может быть null
    /**
     * Создает элемент класса Movie, основываясь на введенных заранее параметрах
     * @param name название фильма
     * @param coordinates статус просмотра и оценка пользователя
     * @param oscarsCount количество оскаров у фильма
     * @param genre жанр фильма
     * @param mpaaRating возрастное ограничение фильма
     * @param director режиссер фильма
     */
    public Movie(String name, Coordinates coordinates, int oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person director){
        creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.director = director;

    }
    /**
     * Пустой конструктор, создает элемент класса Movie, в котором все поля равны null.
     * Нужен для последующей обработки утилитой MovieCreator
     */
    public Movie(){
        coordinates = new Coordinates(0,0);
    }
    /**
     * Возвращает ID, принадлежащий фильму
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Устанавливает фильму данный ID
     * @param id ID, который нужно присвоить
     */
    public void setId(int id){
        this.id = id;
    }
    /**
     * Возвращает название фильма
     * @return название
     */
    public String getName() {
        return name;
    }
    /**
     * Устанавливает фильму данное название
     * @param name имя, которое нужно присвоить
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Возвращает статус просмотра и оценку пользователя, принадлежащие фильму
     * @return статус просмотра и оценку
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /**
     * Возвращает статус просмотра фильма
     * @return статус просмотра фильма
     */
    public int getX(){
        return coordinates.getX();
    }
    /**
     * Возвращает пользовательскую оценку фильма
     * @return пользовательскую оценку фильма
     */
    public long getY(){
        return coordinates.getY();
    }
    /**
     * Устанавливает фильму данные статус просмотра и пользовательскую оценку
     * @param coordinates статус просмотра и пользовательская оценка
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    /**
     * Возвращает время создания фильма
     * @return время создания
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    /**
     * Обновляет значение время создания фильма
     */
    public void updateCreationDate(){
        creationDate = LocalDateTime.now();
    }
    /**
     * Устанавливает фильму данное время создания
     * @param time время создания
     */
    public void setCreationDate(LocalDateTime time){
        creationDate = time;
    }
    /**
     * Возвращает количество оскаров у фильма
     * @return количество оскаров
     */
    public int getOscarsCount() {
        return oscarsCount;
    }
    /**
     * Устанавливает фильму данное количество оскаров
     * @param oscarsCount количество оскаров
     */
    public void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }
    /**
     * Возвращает жанр фильма, если он не null
     * @return жанр
     */
    public MovieGenre getGenre() {
        return genre;
    }
    /**
     * Возвращает строковое представление жанра фильма или null
     * @return жанр фильма в виде строки или null
     */
    public String genreToString(){
        if (genre == null) return null;
        else return genre.toString();
    }
    /**
     * Устанавливает фильму данный жанр
     * @param genre жанр
     */
    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }
    /**
     * Возвращает возрастное ограничение фильма
     * @return возрастное ограничение
     */
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }
    /**
     * Устанавливает фильму данное возрастное ограничение
     * @param mpaaRating возрастное ограничение
     */
    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }
    /**
     * Возвращает режиссера фильма
     * @return режиссера
     */
    public Person getDirector() {
        return director;
    }
    /**
     * Устанавливает данного человека как директора фильма
     * @param director режиссер
     */
    public void setDirector(Person director) {
        this.director = director;
    }
    /**
     * Возвращает строковое представление фильма в виде всех полей фильма и подписей к ним
     * @return строковое представление фильма
     */
    @Override
    public String toString() {
        return ("ID: " + id + ", Фильм: " + name + ", Просмотрен ли фильм: " + coordinates.getX() +
                ", Оценка пользователя: " + coordinates.getY() + ", Количество Оскаров: " + oscarsCount +
                ", Жанр: " + genre +
                ", Возрастное ограничение: " + mpaaRating + ", Сценарист: " + director +
                ", Время добавления в коллекцию: " + creationDate);
    }
    /**
     * Сравнивает два фильма по их названиям
     */
    @Override
    public int compareTo(Movie movie){
        return name.compareTo(movie.name);
    }
    /**
     * Возвращает поля фильма в качестве массива строк
     * @return массив строк со значениями полей фильма
     */
    public String[] getFields(){
        return new String[]{String.valueOf(id), name, String.valueOf(coordinates.getX()),
                String.valueOf(coordinates.getY()), String.valueOf(oscarsCount),
                genreToString(), mpaaRating.name(), director.getName(),
                String.valueOf(director.getWeight()), director.getEyeColor().toString(),
                creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)};
    }
    /**
     * Возвращает хэшкод фильма, основанный на названии фильма и его режиссере
     */
    @Override
    public int hashCode(){
        return Objects.hash(name, director);
    }
}
