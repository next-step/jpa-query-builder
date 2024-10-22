package persistence.sql.dml.delete;

import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;

public class DeleteQueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(DeleteQueryBuilder.class);

    private DeleteQueryBuilder() {
    }

    public static String generateQuery(Class<?> entityClass, Object entity) {
        String tableName = NameUtils.getTableName(entityClass);
        Field idColumnField = getIdColumnField(entityClass);
        String idColumnName = NameUtils.getColumnName(idColumnField);
        idColumnField.setAccessible(true);
        Object idValue;
        try {
            idValue = idColumnField.get(entity);
        } catch (IllegalAccessException e) {
            logger.error("Error while generating query!");
            throw new RuntimeException(e);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("delete from ")
                .append(tableName)
                .append(" where ")
                .append(idColumnName)
                .append(" = ")
                .append(idValue)
                .append(";");
        return stringBuilder.toString();
    }

    public static String generateQuery(Class<?> entityClass, String id) {
        String tableName = NameUtils.getTableName(entityClass);
        String idColumnName = NameUtils.getColumnName(getIdColumnField(entityClass));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("delete from ")
                .append(tableName)
                .append(" where ")
                .append(idColumnName)
                .append(" = ")
                .append(id)
                .append(";");
        return stringBuilder.toString();
    }

    private static Field getIdColumnField(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("Inappropriate entity class!");
    }
}
