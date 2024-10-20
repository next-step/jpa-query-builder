package persistence.sql.metadata;

import static persistence.validator.AnnotationValidator.isNotBlank;
import static persistence.validator.AnnotationValidator.isNotPresent;
import static persistence.validator.AnnotationValidator.isPresent;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.exception.NotExistException;

public record TableName(String value) {

    public TableName(Class<?> clazz) {
        this(getName(clazz));
    }

    private static String getName(Class<?> clazz) {
        if (isNotPresent(clazz, Entity.class)) {
            throw new NotExistException("@Entity annotation. class = " + clazz.getName());
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
