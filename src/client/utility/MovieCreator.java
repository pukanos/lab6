package client.utility;

import common.classes.*;
import common.exceptions.InvalidScriptInputException;

import java.util.Scanner;
/**
 * Класс, отвечающий за создание фильма как элемента коллекции.
 */
public class MovieCreator {
    Scanner scanner;
    private boolean scriptMode;
    /**
     * Создает элемент класса MovieCreator для создания элементов коллекции.
     * @param scanner откуда идет ввод данных
     */
    public MovieCreator(Scanner scanner){
        this.scanner = scanner;
        scriptMode = false;
    }
    /**
     * Возвращает используемый в данный момент сканер.
     * @return сканер
     */
    public Scanner getScanner(){
        return scanner;
    }
    /**
     * Устанавливает сканер, с которого будет идти ввод.
     * @param scanner сканер, с которого будет идти ввод
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    /**
     * Устанавливает режим работы со скриптом.
     */
    public void setScriptMode(){
        scriptMode = true;
    }
    /**
     * Устанавливает режим работы с пользовательским вводом.
     */
    public void setInteractiveMode(){
        scriptMode = false;
    }
    /**
     * Создает название фильма.
     * @return название
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public String createName() throws InvalidScriptInputException {
        while (true) {
            System.out.println("Введите название фильма");
            String name = scanner.nextLine().trim();
            if (scriptMode) System.out.println(name);
            if (name.isEmpty()) {
                System.err.println("Введено пустое значение!");
                if (scriptMode) throw new InvalidScriptInputException();
            } else {
                return name;
            }
        }
    }
    /**
     * Создает объект, хранящий в себе статус просмотра фильма и пользовательскую оценку.
     * @return объект, хранящий в себе статус просмотра фильма и пользовательскую оценку
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public Coordinates createCoordinates() throws InvalidScriptInputException{
        int x = createX();
        if (x==0) return new Coordinates(x, 0L);
        return new Coordinates(x,createY());
    }
    /**
     * Создает статус просмотра фильма.
     * @return статус просмотра
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public int createX() throws InvalidScriptInputException {
        while (true){
            try {
                System.out.println("Смотрели ли Вы этот фильм? 0 - нет, 1 - да");
                int x = Integer.parseInt(scanner.nextLine());
                if (scriptMode) System.out.println(x);
                if (x == 1 || x == 0){
                    return x;
                }
                else if (scriptMode) throw new InvalidScriptInputException();
                else{
                    System.err.println("Введено неверное значение. Введите 0, если фильм не просмотрен, или 1 в ином случае");
                }
            }
            catch (NumberFormatException e){
                System.err.println("Введено не число, попробуйте еще раз");
                if (scriptMode) throw new InvalidScriptInputException();
            }
        }
    }
    /**
     * Создает пользовательскую оценку фильма, если тот был просмотрен.
     * @return оценку фильма
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public long createY() throws InvalidScriptInputException{
        while (true){
            try{
                System.out.println("Введите вашу оценку фильму по шкале от 0 до 10");
                long y = Long.parseLong(scanner.nextLine().trim().toLowerCase());
                if (scriptMode) System.out.println(y);
                if (y < 0 | y > 10){
                    if (scriptMode) throw new InvalidScriptInputException();
                    System.err.println("Введено неверное значение. Введите вашу оценку фильму по шкале от 0 до 10");
                }
                else{
                    return y;
                }
            }
            catch (NumberFormatException e){
                System.err.println("Введено не число, попробуйте еще раз");
                if (scriptMode) throw new InvalidScriptInputException();
            }
        }
    }
    /**
     * Создает человека.
     * @return человека
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public Person createPerson() throws InvalidScriptInputException{
        return new Person(createPersonName(), createPersonWeight(), createPersonEyeColor());
    }
    /**
     * Создает имя человека.
     * @return имя
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public String createPersonName() throws InvalidScriptInputException{
        while (true){
//            try {
                System.out.println("Введите имя сценариста фильма");
                String name = scanner.nextLine().trim().toLowerCase();
                if (scriptMode) System.out.println(name);
                if (name.isEmpty()) {
                    System.err.println("Введено пустое значение!");
                    if (scriptMode) throw new InvalidScriptInputException();
                }
                else{
                    return name;
                }
            }
    }
    /**
     * Создает вес человека.
     * @return вес
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public double createPersonWeight() throws InvalidScriptInputException{
        while (true) {
            try {
                System.out.println("Введите вес сценариста");
                double weight = Double.parseDouble(scanner.nextLine());
                if (scriptMode) System.out.println(weight);
                if (weight < 0) {
                    System.err.println("Введено некорректное значение!");
                    if (scriptMode) throw new InvalidScriptInputException();
                }
                else {
                    return weight;
                }
            } catch (NumberFormatException e) {
                System.err.println("Введено не число!");
                if (scriptMode) throw new InvalidScriptInputException();
            }
        }
    }
    /**
     * Создает цвет глаз человека.
     * @return цвет глаз
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public Color createPersonEyeColor() throws InvalidScriptInputException{
        while (true){
            System.out.println("Выберите цвет глаз сценариста из следующего списка: WHITE, YELLOW, ORANGE");
            String color = scanner.nextLine().trim().toUpperCase();
            if (scriptMode) System.out.println(color);
            if (color.isEmpty()) {
                System.err.println("Введено пустое значение!");
                if (scriptMode) throw new InvalidScriptInputException();
            }
            else if (color.equals("WHITE") | color.equals("YELLOW") | color.equals("ORANGE")) {
                return Color.valueOf(color);
            } else{
                System.err.println("Некорректно задан цвет!");
                if (scriptMode) throw new InvalidScriptInputException();
            }
        }
    }
    /**
     * Создает количество оскаров фильма.
     * @return количество оскаров
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public int createOscarsCount() throws InvalidScriptInputException{
        while (true){
            try {
                System.out.println("Введите количество оскаров");
                int oscarsCount = Integer.parseInt(scanner.nextLine());
                if (scriptMode) System.out.println(oscarsCount);
                if (oscarsCount > 0) {
                    return oscarsCount;
                }
                else{
                    System.err.println("Введено некорректное значение! Введите число больше 0!");
                    if (scriptMode) throw new InvalidScriptInputException();
                }
            }
            catch (NumberFormatException e){
                System.err.println("Введено не число!");
                if (scriptMode) throw new InvalidScriptInputException();
            }
        }
    }
    /**
     * Создает рейтинг фильма в системе MPAA.
     * @return рейтинг фильма
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public MpaaRating createMpaaRating() throws InvalidScriptInputException{
        while (true){
            try {
                System.out.println("Введите возрастное ограничение фильма из предложенных значений:" +
                        "G, PG, PG_13, R, NC_17");
                String line = scanner.nextLine().toUpperCase().trim();
                if (line.isEmpty()) {
                    System.out.println("Ведено пустое значение!");
                    if (scriptMode) throw new InvalidScriptInputException();
                }
                else {
                    if (scriptMode) System.out.println(line);
                    MpaaRating mpaaRating = MpaaRating.valueOf(line);
                    if (mpaaRating.equals(MpaaRating.G) | mpaaRating.equals(MpaaRating.PG) |
                            mpaaRating.equals(MpaaRating.PG_13) | mpaaRating.equals(MpaaRating.R) |
                            mpaaRating.equals(MpaaRating.NC_17)) {
                        return mpaaRating;
                    }
                }
        }
            catch (IllegalArgumentException e){
                System.err.println("Введено некорректное значение!");
                if (scriptMode) throw new InvalidScriptInputException();
            }
        }
    }
    /**
     * Создает жанр фильма.
     * @return жанр
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public MovieGenre createMovieGenre() throws InvalidScriptInputException {
        while (true){
                System.out.println("Введите жанр фильма(поле можно оставить пустым): " +
                        "WESTERN, TRAGEDY, THRILLER, HORROR");
                String genre = scanner.nextLine().trim().toUpperCase();
                if (scriptMode) System.out.println(genre);
                if (genre.isEmpty()) {
                    return null;
                }
                else if (genre.equals("WESTERN") | genre.equals("TRAGEDY")
                        | genre.equals("HORROR") | genre.equals("THRILLER")) {
                    return MovieGenre.valueOf(genre);
                } else {
                    System.err.println("Некорректно задан жанр!");
                    if (scriptMode) throw new InvalidScriptInputException();
                }
        }
    }
    /**
     * Создает фильм из объекта класса Movie как элемент коллекции.
     * @param movie фильм, который нужно создать
     * @throws InvalidScriptInputException если во время работы скрипта был получен некорректный ввод
     */
    public void createMovie(Movie movie) throws InvalidScriptInputException{
        movie.setName(createName());
        movie.setCoordinates(createCoordinates());
        movie.setDirector(createPerson());
        movie.setOscarsCount(createOscarsCount());
        movie.setMpaaRating(createMpaaRating());
        movie.setGenre(createMovieGenre());
        movie.updateCreationDate();
    }
}
