package persistence.sql.ddl.util;

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
