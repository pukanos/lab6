package server.utility;

import common.classes.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 * Класс, проверяющий валидность данных коллекции.
 */
public class Validator {
    private final LinkedList<Movie> movieCollection;
    /**
     * Создает элемент класса Validator для проверки коллекции.
     * @param movieCollection коллекция для проверки
     */
    public Validator(LinkedList<Movie> movieCollection){
        this.movieCollection = movieCollection;
    }
    /**
     * Проверяет коллекцию, проходя по каждому ее элементу.
     * @return movieCollection коллекцию, из которой были удалены невалидные элементы
     */
    public LinkedList<Movie> validate() throws IllegalArgumentException{
        List<Integer> ids = new ArrayList<>();

        for (Iterator<Movie> iterator = movieCollection.iterator(); iterator.hasNext();){
            Movie movie = iterator.next();
            if (movie.getId() <= 0 || movie.getId() == null || ids.contains(movie.getId())){
                iterator.remove();
            }
            if (movie.getName().equals("null") || movie.getName().isEmpty() || movie.getName() == null){
                iterator.remove();
            }
            if (movie.getX() == 0 && movie.getY() != 0){
                iterator.remove();
            }
            if (!(movie.getX() != 0 || movie.getX() != 1)){
                iterator.remove();
            }
            if (movie.getY() > 10 || movie.getY() < 0){
                iterator.remove();
            }
            if (movie.getCreationDate() == null || movie.getCreationDate().equals("")){
                iterator.remove();
            }
            if (movie.getOscarsCount() < 0){
                iterator.remove();
            }
            if (!(movie.getGenre() == MovieGenre.WESTERN || movie.getGenre() == MovieGenre.HORROR ||
                    movie.getGenre() == MovieGenre.TRAGEDY || movie.getGenre() == MovieGenre.THRILLER || movie.getGenre() == null)){
                iterator.remove();
            }
            if (!(movie.getDirector().getEyeColor() == (Color.WHITE) || movie.getDirector().getEyeColor() == (Color.YELLOW) ||
                    movie.getDirector().getEyeColor() == (Color.ORANGE))){
                iterator.remove();
            }
            if (movie.getDirector().getName().equals("null") || movie.getDirector().getName().isEmpty()){
                iterator.remove();
            }
            if (movie.getDirector().getWeight() < 0){
                iterator.remove();
            }
            if (!(movie.getMpaaRating() == MpaaRating.G || movie.getMpaaRating() == MpaaRating.PG ||
                    movie.getMpaaRating() == MpaaRating.PG_13 ||movie.getMpaaRating() == MpaaRating.R ||
                    movie.getMpaaRating() == MpaaRating.NC_17)){
                iterator.remove();
            }

            ids.add(movie.getId());
        }
        return movieCollection;

    }
}
