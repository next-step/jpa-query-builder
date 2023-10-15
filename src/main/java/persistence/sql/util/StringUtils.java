package persistence.sql.util;

public class StringUtils {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
