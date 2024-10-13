package persistence.sql.ddl.model;

import jakarta.persistence.Table;
import persistence.sql.ddl.ExceptionUtil;

public class TableName {
    private final String value;

    public TableName(Class<?> clazz) {
        ExceptionUtil.requireNonNull(clazz);

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
