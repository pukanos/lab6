package common.classes;

import java.io.Serializable;
import java.util.Objects;
/**
 * Класс, описывающий человека.
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 6L;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double weight; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    /**
     * Создает человека.
     * @param name имя человека
     * @param weight вес человека
     * @param eyeColor цвет глаз человека
     */
    public Person(String name, double weight, Color eyeColor){
        this.name = name;
        this.weight = weight;
        this.eyeColor = eyeColor;
    }
    /**
     * Возвращает имя человека.
     * @return имя
     */
    public String getName() {
        return name;
    }
    /**
     * Задает человеку данное имя.
     * @param name имя
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Возвращает вес человека.
     * @return вес
     */
    public double getWeight() {
        return weight;
    }
    /**
     * Задает человеку данный вес.
     * @param weight вес
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    /**
     * Возвращает цвет глаз человека.
     * @return цвет глаз
     */
    public Color getEyeColor() {
        return eyeColor;
    }
    /**
     * Задает человеку данный цвет глаз.
     * @param eyeColor цвет
     */
    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }
    /**
     * Выводит имя, вес, цвет глаз человека.
     * @return имя, вес, цвет
     */
    @Override
    public String toString() {
        return ("Имя: " + name + ", Вес: " + weight + ", Цвет глаз: " + eyeColor);
    }
    /**
     * Выводит hashCode, основанный на имени, весе, цвете глаз человека.
     * @return hashCode
     */
    @Override
    public int hashCode(){
        return Objects.hash(name, weight, eyeColor);
    }
}
