package database.sql.ddl;

import java.util.ArrayList;
import java.util.List;

public class EntityColumn {
    private final String propertyName;
    private final String columnName;
    private final Class<?> type;
    private final boolean isPrimaryKey;
    private final boolean autoIncrement;
    private final boolean nullable;

    public EntityColumn(String propertyName,
                        String columnName,
                        Class<?> type,
                        boolean isPrimaryKey,
                        boolean autoIncrement,
                        boolean nullable) {
        this.propertyName = propertyName;
        this.columnName = columnName;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
        this.autoIncrement = autoIncrement;
        this.nullable = nullable;
    }

    public String toColumnDefinition() {
        List<String> list = new ArrayList<>();

        list.add(columnName);

        String e = convertType(type);
        list.add(e);

        if (autoIncrement) {
            list.add("AUTO_INCREMENT");
        }
        if (isPrimaryKey) {
            list.add("PRIMARY KEY");
        }

        if (!isPrimaryKey) {
            if (nullable) {
                list.add("NULL");
            } else {
                list.add("NOT NULL");
            }
        }

        return String.join(" ", list);
    }

    private String convertType(Class<?> type) {
        switch (type.getName()) {
            case "java.lang.Long":
                return "BIGINT";
            case "java.lang.String":
                return "VARCHAR(100)";
            case "java.lang.Integer":
                return "INT";
            default:
                throw new RuntimeException("Cannot convert type: " + type.getName());
        }
    }
}
