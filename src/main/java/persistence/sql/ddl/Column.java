package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return primaryKey == column.primaryKey && Objects.equals(name, column.name) && columnType == column.columnType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columnType, primaryKey);
    }
}
