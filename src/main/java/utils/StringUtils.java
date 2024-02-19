package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils() {
    }

    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("([a-z])([A-Z])");

    public static String convertCamelToSnakeString(String str) {
        Matcher matcher = CAMEL_CASE_PATTERN.matcher(str);
        return matcher.replaceAll(matchResult -> String.format(
            "%s_%s",
            matchResult.group(1).toLowerCase(),
            matchResult.group(2).toUpperCase()
        )).toLowerCase();
    }
}
