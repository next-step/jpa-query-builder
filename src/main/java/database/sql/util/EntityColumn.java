package database.sql.util;

import java.util.ArrayList;
import java.util.List;

public class EntityColumn {
    private final String columnName;
    private final Class<?> type;
    private final Integer columnLength;
    private final boolean isPrimaryKey;
    private final boolean autoIncrement;
    private final boolean nullable;
    private final EntityFieldTypeConverter entityFieldTypeConverter;

    public EntityColumn(String columnName,
                        Class<?> type,
                        Integer columnLength,
                        boolean isPrimaryKey,
                        boolean autoIncrement,
                        boolean nullable,
                        EntityFieldTypeConverter entityFieldTypeConverter) {
        this.columnName = columnName;
        this.type = type;
        this.columnLength = columnLength;
        this.isPrimaryKey = isPrimaryKey;
        this.autoIncrement = autoIncrement;
        this.nullable = nullable;
        this.entityFieldTypeConverter = entityFieldTypeConverter;
    }

    public String getColumnName() {
        return columnName;
    }

    public String toColumnDefinition() {
        List<String> list = new ArrayList<>();

        list.add(columnName);

        list.add(entityFieldTypeConverter.convert(type, columnLength));

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
}
