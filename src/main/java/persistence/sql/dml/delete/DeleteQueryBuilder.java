package persistence.sql.dml.delete;

import jakarta.persistence.Id;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;

public class DeleteQueryBuilder {
    private DeleteQueryBuilder() {
    }

    public static String generateQuery(Class<?> entityClass, Object entity) throws IllegalAccessException {
        String tableName = NameUtils.getTableName(entityClass);
        Field idColumnField = getIdColumnField(entityClass);
        String idColumnName = NameUtils.getColumnName(idColumnField);
        idColumnField.setAccessible(true);
        Object idValue = idColumnField.get(entity);

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
