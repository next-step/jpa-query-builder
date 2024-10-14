package persistence.sql.common.util;

/**
 * 카멜 케이스 변환기
 */
public class ToCamelCaseConverter implements NameConverter {
    private static final ToCamelCaseConverter INSTANCE = new ToCamelCaseConverter();

    private ToCamelCaseConverter() {}

    public static ToCamelCaseConverter getInstance() {
        return INSTANCE;
    }

    public String convert(String name) {
        StringBuilder builder = new StringBuilder();
        String[] words = name.split("[\\W_]+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i == 0) {
                word = word.isEmpty() ? word : word.toLowerCase();
            } else {
                word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            }
            builder.append(word);
        }
        return builder.toString();
    }
}
