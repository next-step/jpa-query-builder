package persistence.sql.dml;

public enum DataLanguage {

    CREATE("CREATE TABLE"),
    DROP("DROP TABLE"),
    INSERT("INSERT INTO"),
    VALUES("VALUES"),
    SELECT("SELECT"),
    DELETE("DELETE"),
    FROM("FROM"),
    WHERE("WHERE"),
    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    COMMA(","),
    EQUALS("="),
    SEMICOLON(";");

    private final String name;

    DataLanguage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
