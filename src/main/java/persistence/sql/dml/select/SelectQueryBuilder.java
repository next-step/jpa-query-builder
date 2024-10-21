package persistence.sql.dml.select;

import jakarta.persistence.Id;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;

public class SelectQueryBuilder {
    private SelectQueryBuilder() {
    }

    public static String generateQuery(Class<?> entityClass) {
        String tableName = NameUtils.getTableName(entityClass);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("select * from ")
                .append(tableName)
                .append(";");
        return stringBuilder.toString();
    }

    public static String generateQuery(Class<?> entityClass, String id) {
        String tableName = NameUtils.getTableName(entityClass);
        String idColumnName = NameUtils.getColumnName(getIdColumn(entityClass));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("select * from ")
                .append(tableName)
                .append(" where ")
                .append(idColumnName)
                .append(" = ")
                .append(id)
                .append(";");
        return stringBuilder.toString();
    }

    private static Field getIdColumn(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("Inappropriate entity class!");
    }
}
