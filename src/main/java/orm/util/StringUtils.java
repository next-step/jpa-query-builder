package orm.util;

public class StringUtils {

    public static boolean isBlank(String string) {
        if (string == null) {
            return true;
        }
        return string.isBlank();
    }

    public static boolean isNotBlank(String string) {
        if (string == null) {
            return false;
        }
        return !string.isBlank();
    }
}
