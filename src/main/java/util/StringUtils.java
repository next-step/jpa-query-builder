package util;

public class StringUtils {

    private StringUtils() {}

    public static boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }

}
