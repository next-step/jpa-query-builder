package persistence.sql.ddl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

public class ColumnName {

    private final Field field;

    public ColumnName(Field field) {
        this.field = field;
    }

    public String getColumnName() {
        String columnName;
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name().isEmpty() ? field.getName() : column.name();
        } else {
            columnName = field.getName();
        }
        return columnName;
    }

}
