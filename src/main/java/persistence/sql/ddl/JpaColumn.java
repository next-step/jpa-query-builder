package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class JpaColumn {
    protected static final String SPACE = " ";

    protected String name;
    private final String nullable;
    protected ColumnType columnType;

    public JpaColumn(Field field) {
        this.name = convertName(field);
        this.nullable = getNullable(field.getAnnotation(Column.class).nullable());
        this.columnType = ColumnType.toDdl(field.getType());
    }

    public JpaColumn(String name, boolean nullable, ColumnType columnType) {
        this.name = name;
        this.nullable = getNullable(nullable);
        this.columnType = columnType;
    }

    private String getNullable(boolean isNullable) {
        if (isNullable) {
            return "";
        }
        return SPACE + "not null";
    }

    private String convertName(Field field) {
        String columnName = field.getAnnotation(Column.class).name();
        if (columnName.isBlank() || columnName.isEmpty()) {
            return field.getName();
        }
        return columnName;
    }

    String getDefinition() {
        return this.name + columnType.getColumnDefinition() + nullable + ", ";
    }

    public String getName() {
        return name;
    }
}
