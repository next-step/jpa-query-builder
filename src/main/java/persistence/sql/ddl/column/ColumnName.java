package persistence.sql.ddl.column;

import util.StringUtil;

import java.lang.reflect.Field;

public class ColumnName {

    private final String name;

    private ColumnName(String name) {
        this.name = StringUtil.camelToSnake(name);
    }

    public static ColumnName from(Field field) {
        jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);

        if (column == null || column.name().isEmpty()) {
            return new ColumnName(field.getName());
        }

        return new ColumnName(column.name());
    }

    public String getName() {
        return name;
    }
}
