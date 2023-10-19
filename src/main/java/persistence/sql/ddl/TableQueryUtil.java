package persistence.sql.ddl;

import jakarta.persistence.Table;

public class TableQueryUtil {
    public static String getTableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table annotation = clazz.getAnnotation(Table.class);
            if (!annotation.name().isEmpty()) {
                tableName = annotation.name().toLowerCase();
            }
        }

        return tableName;
    }
}
