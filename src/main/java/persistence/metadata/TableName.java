package persistence.metadata;

import common.ErrorCode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static common.AnnotationValidation.*;

public record TableName(String name) {
    public TableName(Class<?> clazz) {
        this(getName(clazz));
    }

    private static String getName(Class<?> clazz) {
        if (isNotPresent(clazz, Entity.class)) {
            throw new IllegalArgumentException(ErrorCode.NOT_EXIST_ENTITY_ANNOTATION.getErrorMsg());
        }

        if (isTableNamePresent(clazz)) {
            return getTableName(clazz);
        }
        return getEntityNameOrSimpleName(clazz);
    }
    private static boolean isTableNamePresent(Class<?> clazz) {
        return isPresent(clazz, Table.class) && isNotBlank(getTableName(clazz));
    }
    private static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        return table.name();
    }
    private static String getEntityNameOrSimpleName(Class<?> clazz) {
        Entity entity = clazz.getAnnotation(Entity.class);
        if (isNotBlank(entity.name())) {
            return entity.name();
        }
        return clazz.getSimpleName();
    }
}
