package util;

import java.util.regex.Pattern;

public class CaseConverter {

    private final static Pattern caseSensitiveWordPattern = Pattern.compile("([a-z])([A-Z])");

    public static String pascalToSnake(String pascalCase) {
        return caseSensitiveWordPattern.matcher(pascalCase)
                .replaceAll(matchResult -> {
                    String group1 = matchResult.group(1);
                    String group2 = matchResult.group(2);
                    return String.format("%s_%s", group1, group2);
                })
                .toLowerCase();
    }

    public static String pascalToCamel(String pascalCase) {
        StringBuilder stringBuilder = new StringBuilder(pascalCase);

        char firstChar = stringBuilder.charAt(0);
        char lowerFirstChar = Character.toLowerCase(firstChar);
        stringBuilder.setCharAt(0, lowerFirstChar);

        return stringBuilder.toString();
    }
}
