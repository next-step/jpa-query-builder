package persistence.sql.common;

import persistence.exception.InvalidTypeException;

public class ColumnType {
    private final String type;

    private ColumnType(String type) {
        this.type = parse(type);
    }

    public static ColumnType initType(String type) {
        return new ColumnType(type);
    }

    private String parse(String type) {
        switch (type) {
            case "int":
                return "INT";
            case "Integer":
                return "INTEGER";
            case "Long":
                return "BIGINT";
            case "String":
                return "VARCHAR(255)";
        }

        throw new InvalidTypeException();
    }

    public String getType() {
        return " " + type;
    }
}
