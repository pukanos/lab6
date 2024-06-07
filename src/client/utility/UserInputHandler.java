package client.utility;

import common.classes.Movie;
import common.Request;
import common.exceptions.InvalidScriptInputException;
import common.exceptions.RecursiveScriptException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class UserInputHandler {
    private Scanner scanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();
    public UserInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }
    public boolean scriptMode(){
        return !scannerStack.isEmpty();
    }
    public Stack<Scanner> getScannerStack(){
        return scannerStack;
    }
    public Stack<File> getScriptStack(){
        return scriptStack;
    }
    public Request handle(String[] command) {
        ProcessingCode processingCode;
                try{
                    if (scriptMode() && !scanner.hasNextLine()){
                        scanner.close();
                        scanner = scannerStack.pop();
                        System.out.println("Завершение работы скрипта " + scriptStack.pop());
                        return new Request();
                    }
                    if (scriptMode()){
                        String scriptInput = scanner.nextLine();
                        if (!scriptInput.isEmpty()) System.out.println(scriptInput);
                        command = (scriptInput.trim().toLowerCase() + " ").split(" ",2);
                        command[1] = command[1].trim();
                    }
                } catch(Exception e){
                    System.err.println("Error: " + e.getMessage());
                }
//                System.out.println("AAAAAAAAAAAAAAAAAAAAAAA    " + Arrays.toString(command));
                processingCode = processingCommand(command[0], command[1]);
            try {
                if (scriptMode() && (processingCode == ProcessingCode.ERROR))
                    throw new InvalidScriptInputException();
                switch (processingCode){
                    case OK -> {
                        if (command[0].equals("save")) return new Request();
                        return new Request(command[0], command[1]);}
                    case OBJECT -> {
                        MovieCreator movieCreator = new MovieCreator(scanner);
                        if (scriptMode()) movieCreator.setScriptMode();
                        Movie movie = new Movie();
                        movieCreator.createMovie(movie);
                        return new Request(command[0], command[1], movie);
                    }
                    case UPDATE_OBJECT -> {
                        MovieCreator movieCreator = new MovieCreator(scanner);
                        if (scriptMode()) movieCreator.setScriptMode();
                        return createMovieForUpdate(command, movieCreator);
                    }
                    case SCRIPT -> {
                        File scriptFile = new File(command[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new RecursiveScriptException();
                        scriptStack.push(scriptFile);
                        scannerStack.push(scanner);
                        scanner = new Scanner(scriptFile);
                        System.out.println("Выполнение следующего скрипта: " + command[1]);
                    }
                }
            } catch (InvalidScriptInputException e){
                System.err.println("Ошибка во время выполнения скрипта!");
                while (!scannerStack.isEmpty()){
                    scanner = scannerStack.pop();
                }
                scriptStack.clear();
                return new Request();
            } catch (RecursiveScriptException e){
                System.err.println("Во время выполнения скрипта была получена рекурсия!");
                while (!scannerStack.isEmpty()){
                    scanner = scannerStack.pop();
                }
                scriptStack.clear();
                return new Request();
            } catch (FileNotFoundException e){
                System.err.println("Файл со скриптом не найден!");
            }
        return new Request(command[0], command[1]);
    }

    private Request createMovieForUpdate(String[] command, MovieCreator movieCreator) {
        Movie movie = new Movie();
        boolean generalFlag = true;
        while (generalFlag){
            try {
                System.out.println("Введите 1, если Вы хотите изменить элемент целиком," +
                        " 2, если только выбранные значения и 0, чтобы ничего не изменять");
                int option = Integer.parseInt(scanner.nextLine().trim());
                if (option == 1){
                    movieCreator.createMovie(movie);
                    break;
                } else if (option == 2) {
                    boolean flag = true;
                    while (flag){
                        System.out.println("Что вы хотите изменить: name, viewing status, viewer rating, oscars count," +
                                "genre, mpaarating, director? (Чтобы выйти, введите 'exit')");
                        String line = scanner.nextLine().trim().toLowerCase();
                        String[] ll = (line + " ").split(" ", 2);
                        switch (line){
                            case "name" -> movie.setName(movieCreator.createName());
                            case "viewing status" -> {
                                movie.getCoordinates().setX(movieCreator.createX());
                                if (movie.getCoordinates().getX() == 1){
                                    movie.getCoordinates().setY(movieCreator.createY());
                                }
                                else{
                                    movie.getCoordinates().setY(0);
                                }
                            }
                            case "viewer rating"-> {
                                if (movie.getCoordinates().getX() == 1) movie.getCoordinates().setY(movieCreator.createY());
                                else System.out.println("Посмотрите фильм, прежде чем ставить оценку");
                            }
                            case "oscars count"-> movie.setOscarsCount(movieCreator.createOscarsCount());
                            case "genre"-> movie.setGenre(movieCreator.createMovieGenre());
                            case "mpaarating"-> movie.setMpaaRating(movieCreator.createMpaaRating());
                            case "director"-> movie.setDirector(movieCreator.createPerson());
                            case "exit" -> generalFlag = false;
                            default -> {
                                System.err.println("Поле введено неверно!");
                                continue;
                            }
                        }
                        if (ll[0].equals("exit")) break;
                        while (true){
                            System.out.println("Хотите ли вы изменить что-нибудь еще? (+/-)");
                            String b = scanner.nextLine().trim().toLowerCase();
                            if (b.equals("+")){
                                break;
                            } else if (b.equals("-")) {
                                generalFlag = false;
                                flag = false;
                                break;
                            }
                            else{
                                System.out.println("Введите +/-");
                            }
                        }
                    }
                } else if (option == 0) {
                    break;
                } else {
                    System.err.println("Введено неверное значение!");
                }

            }
            catch (NumberFormatException e){
                System.err.println("Введено не число!");
            } catch (InvalidScriptInputException ignored) {}
        }
        return new Request(command[0], command[1], movie);
    }

private ProcessingCode processingCommand(String command, String argument){
    switch (command){
        case "", "clear", "exit", "help", "history", "info",
             "print_ascending", "print_unique_oscars_count", "show",
             "filter_less_than_director", "remove_by_id", "remove_greater" -> {
            if (!argument.isEmpty()) return ProcessingCode.OK;
        }
        case "update" -> {
            if (!argument.isEmpty()) return ProcessingCode.UPDATE_OBJECT;
//            return ProcessingCode.ERROR;
        }
        case "add", "add_if_max" -> {
            if (argument.isEmpty()) return ProcessingCode.OBJECT;
//            return ProcessingCode.ERROR;
        }
        case "execute_script" -> {
            if (!argument.isEmpty()) return ProcessingCode.SCRIPT;
//            return ProcessingCode.ERROR;
        }
        case "save" -> {
            System.out.println("Коллекция будет сохранена только после завершения работы");
        }
        default -> {
            System.out.println("Команда " + command + " не найдена. Введите 'help' для справки.");
//            return ProcessingCode.ERROR;
        }
        }
    return ProcessingCode.OK;
    }
}
