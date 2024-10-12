package persistence.utils;

public class StringUtils {
    private StringUtils() {
    }

    public static String convertToSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toLowerCase();
    }
}
