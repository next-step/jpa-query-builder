package persistence.sql;

import jakarta.persistence.Table;

public class MetadataUtils {
    private final Class<?> clazz;

    public MetadataUtils(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getTableName() {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            return clazz.getSimpleName().toLowerCase();
        }

        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return clazz.getSimpleName().toLowerCase();
    }
}
