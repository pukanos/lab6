package server.utility;

import common.classes.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, отвечающий за работу с файлами.
 */
public class FileManager {
    private String filename;
    /**
     * Задает файл, с которым будет работать fileManager.
     * @param filename имя файла, с которым будет работать fileManager
     */
    public void setFilename(String filename){
        this.filename = filename;
    }
    /**
     * Создает элемент класса FileManager.
     * @param filename имя файла, с которым будет работать fileManager
     */
    public FileManager(String filename){
        this.filename = filename;
    }
    /**
     * Записывает коллекцию в файл.
     * @param movieCollection коллекция, которая будет записана в файл
     */
    public void writeCollection(LinkedList<Movie> movieCollection){
        if (!filename.isEmpty()){
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filename))) {
                for (Movie movie:movieCollection){
                    byte[] buffer = (String.join(", ", movie.getFields()) + "\n").getBytes();
                    bos.write(buffer);
                }
                bos.flush();
                System.out.println("Коллекция была сохранена в файл!");
            } catch (IOException e) {
                System.err.println("Файл не может быть изменен!");
            }
        }
        else System.err.println("Файл не найден или отсутствует доступ к нему!");
    }
    /**
     * Читает коллекцию из файла.
     * @return коллекцию из файла или новую коллекцию, если файл пуст
     */
    public LinkedList<Movie> readCollection(){
        if (!filename.isEmpty()){
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename))){
                int c;
                LinkedList<Movie> collection = new LinkedList<>(); // для полученных из файла фильмов
                StringBuilder fileData = new StringBuilder();// все из файла
                while ((c = bis.read()) != -1){
                    fileData.append((char) c);
                }
                if (fileData.isEmpty()) return new LinkedList<Movie>();

                String[] list = fileData.toString().split("\n");
                for (String fieldsString:list){ // создание и добавление фильмов, используя значения из файла
                    String[] fieldsArray = fieldsString.split(", "); // массив со значениями фильма в виде строк
                    String[] line = new String[11];
                    for (int i = 0; i < fieldsArray.length; i++){
                        line[i] = fieldsArray[i];
                    }
                    String mpaaRatingString = line[6];
                    String eyeColor = line[9];
                    String movieGenre = line[5];
                    List<String> mpaaRatings = Arrays.stream(MpaaRating.values()).map(MpaaRating::name).toList();
                    List<String> eyeColors = Arrays.stream(Color.values()).map(Color::name).toList();
                    List<String> genres = Arrays.stream(MovieGenre.values()).map(MovieGenre::name).toList();
                    if (!mpaaRatings.contains(mpaaRatingString)) continue;
                    if (!eyeColors.contains(eyeColor)) continue;
                    if (!(genres.contains(movieGenre) || movieGenre.isEmpty())) continue;

                    Movie m1 = new Movie(line[1],new Coordinates(Integer.parseInt(line[2]),Integer.parseInt(line[3]))
                            ,Integer.parseInt(line[4]), MovieGenre.valueOf(movieGenre), MpaaRating.valueOf(mpaaRatingString),
                            new Person(line[7], Double.parseDouble(line[8]), Color.valueOf(eyeColor)));

                    LocalDateTime time = LocalDateTime.parse(line[10]);
                    m1.setCreationDate(time);
                    m1.setId(Integer.parseInt(line[0]));
                    collection.add(m1);
                    bis.close();
                }
                Validator validator = new Validator(collection);
                return new LinkedList<>(validator.validate());
            }catch (FileNotFoundException e){
                System.out.println("Файл не найден или отсутствует доступ к нему!");
                System.exit(2);
            }
            catch (IOException e) {
                System.err.println("Файл не может быть открыт!");
                System.exit(1);
            }
        }
        else System.err.println("Файл не найден!");
        return new LinkedList<Movie>();
    }
}
