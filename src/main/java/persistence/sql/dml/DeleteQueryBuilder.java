package persistence.sql.dml;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Field;

public class DeleteQueryBuilder {
    public String delete(Class<?> clazz, Object idValue) {
        String tableName = getTableName(clazz);
        String idField = getIdField(clazz);
        String formattedIdValue = getFormattedId(idValue);
        return String.format("delete FROM %s where %s = %s", tableName, idField, formattedIdValue);
    }

    private static String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            return clazz.getSimpleName().toLowerCase();
        }

        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return clazz.getSimpleName().toLowerCase();
    }


    private String getFormattedId(Object idValue) {
        if (idValue instanceof String) {
            return String.format(("'%s'"), idValue);
        }
        return idValue.toString();
    }

    private String getIdField(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        throw new IllegalArgumentException("@Id 어노테이션이 존재하지 않음");
    }
}
