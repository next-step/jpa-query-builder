package persistence.sql.meta;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.util.StringUtils;

public class EntityMeta {

    public static boolean isEntity(Class<?> clazz) {
        return clazz.getDeclaredAnnotation(Entity.class) != null;
    }

    public static String getTableName(Class<?> clazz) {
        Table tableAnnotation = clazz.getDeclaredAnnotation(Table.class);
        if (tableAnnotation != null && !StringUtils.isNullOrEmpty(tableAnnotation.name())) {
            return tableAnnotation.name();
        }
        return clazz.getSimpleName().toLowerCase();
    }
}
