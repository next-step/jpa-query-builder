package util;

import java.util.regex.Pattern;

public class CaseConverter {

    private CaseConverter() {
    }

    private final static Pattern caseSensitiveWordPattern = Pattern.compile("([a-z])([A-Z])");

    public static String pascalToSnake(String pascalCase) {
        return getSnakeCase(pascalCase);
    }

    public static String pascalToCamel(String pascalCase) {
        StringBuilder stringBuilder = new StringBuilder(pascalCase);

        char firstChar = stringBuilder.charAt(0);
        char lowerFirstChar = Character.toLowerCase(firstChar);
        stringBuilder.setCharAt(0, lowerFirstChar);

        return stringBuilder.toString();
    }

    public static String camelToSnake(String camelCase) {
        return getSnakeCase(camelCase);
    }

    private static String getSnakeCase(String camelCase) {
        return caseSensitiveWordPattern.matcher(camelCase)
                .replaceAll(matchResult -> {
                    String group1 = matchResult.group(1);
                    String group2 = matchResult.group(2);
                    return String.format("%s_%s", group1, group2);
                })
                .toLowerCase();
    }
}
