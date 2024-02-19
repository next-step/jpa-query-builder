package persistence.sql.ddl.h2;

import jakarta.persistence.Table;

public class TableName {
    private final Class<?> clazz;

    public TableName(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getTableName() {
        if (!hasTableAnnotation()) {
            return getTableNameByClass();
        }
        String annotationName = clazz.getAnnotation(Table.class).name();
        if (annotationName.isBlank()) {
            return getTableNameByClass();
        }
        return annotationName;
    }

    private String getTableNameByClass() {
        return clazz.getSimpleName();
    }

    private boolean hasTableAnnotation() {
        return clazz.isAnnotationPresent(Table.class);
    }
}
