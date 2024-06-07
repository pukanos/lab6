package common.classes;

import java.io.Serializable;

/**
 * Класс, отвечающий за хранение статуса просмотра фильма и пользовательской оценки.
 */
public class Coordinates implements Serializable {
    private final static long serialVersionUID = 10L;
    private int x; //Максимальное значение поля: 358
    private long y;
    /**
     * Создает объект, хранящий статус просмотра фильма и пользовательскую оценку.
     * @param x статус просмотра
     * @param y пользовательская оценка
     */
    public Coordinates(int x, long y){
        this.y = y;
        this.x = x;
    }
    /**
     * Возвращает статус просмотра фильма.
     * @return статус просмотра
     */
    public int getX() {
        return x;
    }
    /**
     * Устанавливает данный статус просмотра фильма.
     * @param x статус просмотра
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Возвращает пользовательскую оценку фильма.
     * @return пользовательскую оценку
     */
    public long getY() {
        return y;
    }
    /**
     * Устанавливает данную пользовательскую оценку фильма.
     * @param y пользовательская оценка
     */
    public void setY(long y) {
        this.y = y;
    }
}
