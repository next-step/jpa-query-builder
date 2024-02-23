package persistence.sql.meta.table;

import jakarta.persistence.Table;

public class TableName {
    private final String name;

    public TableName(Class<?> clazz) {
        this.name = extractName(clazz);
    }

    public String getName() {
        return name;
    }

    private String extractName(Class<?> clazz) {
        if (hasTableAnnotation(clazz)) {
            return extractNameByAnnotation(clazz);
        }
        return extractNameByClass(clazz);
    }

    private String extractNameByAnnotation(Class<?> clazz) {
        String annotationName = clazz.getAnnotation(Table.class).name();
        if (annotationName.isBlank()) {
            return extractNameByClass(clazz);
        }
        return annotationName;
    }

    private String extractNameByClass(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    private boolean hasTableAnnotation(Class<?> clazz) {
        return clazz.isAnnotationPresent(Table.class);
    }
}
