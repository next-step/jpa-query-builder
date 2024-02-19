package database.sql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

    public static String quote(Object value) {
        String str = value.toString();
        Matcher matcher = NUMBER_PATTERN.matcher(str);
        if (matcher.matches()) return str;
        return "'" + str + "'";
    }
}
