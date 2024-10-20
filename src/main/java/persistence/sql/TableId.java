package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class TableId {
    private Class<?> type;
    private String name;
    private boolean isAutoIncrement;


    public TableId(Field field) {
        this.type = field.getType();
        this.name = setColumnName(field);
        this.isAutoIncrement = isAutoIncrement(field);
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    private static boolean isAutoIncrement(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class) &&
                field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
    }

    private String setColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }
        return field.getName();
    }
}
