package persistence.sql.model;

import jakarta.persistence.Table;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;

public class TableName {
    private final String value;

    public TableName(Class<?> clazz) {
        if (clazz == null) {
            throw new RequiredClassException(ExceptionMessage.REQUIRED_CLASS);
        }

        this.value = getTableName(clazz);
    }

    private String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName().toLowerCase();
        }

        String name = clazz.getAnnotation(Table.class).name();
        if (name.isEmpty()) {
            return clazz.getSimpleName().toLowerCase();
        }

        return name;
    }

    public String getValue() {
        return this.value;
    }
}
