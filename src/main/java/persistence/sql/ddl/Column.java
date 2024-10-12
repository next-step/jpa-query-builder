package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class Column {

    private final String name;
    private final ColumnType columnType;
    private final boolean primaryKey;

    private Column(String name, ColumnType columnType, boolean primaryKey) {
        this.name = name;
        this.columnType = columnType;
        this.primaryKey = primaryKey;
    }

    public static Column from(Field field) {
        return new Column(
                field.getName(),
                ColumnType.of(field.getType()),
                field.isAnnotationPresent(Id.class)
        );
    }

    public boolean hasPrimaryKey() {
        return primaryKey;
    }

    public String getSqlType() {
        return columnType.getSqlType();
    }

    public String getName() {
        return name;
    }
}
