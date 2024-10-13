package util;

public class SQLUtil {

    public static String SQL_노멀라이즈(String query) {
        return query.replaceAll("\\s*\\r?\\n\\s*", "");
    }
}
