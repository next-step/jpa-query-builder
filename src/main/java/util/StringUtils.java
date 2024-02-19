package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String convertCamelCaseToSnakeCase(String input) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String result = matcher.replaceAll(replacement);

        return result.toLowerCase();
    }

}
