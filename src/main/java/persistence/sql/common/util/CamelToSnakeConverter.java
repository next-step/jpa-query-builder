package persistence.sql.common.util;

/**
 * 카멜 케이스 to 스네이크 케이스 변환기
 */
public class CamelToSnakeConverter implements NameConverter {
    private static final CamelToSnakeConverter INSTANCE = new CamelToSnakeConverter();

    private CamelToSnakeConverter() {}

    public static CamelToSnakeConverter getInstance() {
        return INSTANCE;
    }

    public String convert(String name) {
        return name.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
