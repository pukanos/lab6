package server.utility;

public class ResponsePrinter {
    private static StringBuilder builder = new StringBuilder();
    public static void append(Object object) {
        builder.append(object);
    }
    public static void appendln(Object object) {
        builder.append(object);
        builder.append(System.lineSeparator());
    }
    public static void appendln(){
        builder.append(System.lineSeparator());
    }
    public static void appendError(Object error) {
        builder.append("error:").append(error).append('\n');
    }
    public static void appendTable(Object object1, Object object2) {
        builder.append(String.format("%-37s%-1s%n", object1, object2));
    }
    public static String getString(){
        return builder.toString();
    }
    public static void clear(){
        builder = new StringBuilder();
    }
}
