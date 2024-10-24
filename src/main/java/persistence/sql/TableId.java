package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class TableId {
    private final Class<?> type;
    private final String name;
    private final boolean isAutoIncrement;



    public TableId(Field field) {
        this.type = field.getType();
        this.name = idName(field);
        this.isAutoIncrement = isAutoIncrement(field);
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public String name() {
        return name;
    }

    public Class<?> type() {
        return type;
    }

    private boolean isAutoIncrement(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class) &&
                field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
    }

    private String idName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }
        return field.getName();
    }
}
