package orm.util;

import static java.util.Locale.ENGLISH;

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

    // 첫 글자 대문자화
    public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }
}
