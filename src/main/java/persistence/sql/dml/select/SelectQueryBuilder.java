package persistence.sql.dml.select;

import jakarta.persistence.Id;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectQueryBuilder {
    private String tableName;
    private String idColumnName;
    private Map<String, Object> whereCondition = new HashMap<>();

    private SelectQueryBuilder() {
    }

    public static SelectQueryBuilder newInstance() {
        return new SelectQueryBuilder();
    }

    public SelectQueryBuilder entityClass(Class<?> entityClass) {
        this.tableName = NameUtils.getTableName(entityClass);
        this.idColumnName = NameUtils.getColumnName(getIdColumn(entityClass));
        return this;
    }

    private Field getIdColumn(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("Inappropriate entity class!");
    }

    public SelectQueryBuilder whereCondition(Map<String, Object> whereCondition) {
        this.whereCondition.putAll(whereCondition);
        return this;
    }

    public SelectQueryBuilder whereIdCondition(String id) {
        this.whereCondition.put(idColumnName, id);
        return this;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder
                .append("select ")
                .append("* ")
                .append("from ")
                .append(tableName);

        setCondition(stringBuilder);

        return stringBuilder.append(";").toString();
    }

    private void setCondition(StringBuilder stringBuilder) {
        if (whereCondition.isEmpty()) {
            return;
        }

        stringBuilder
                .append(" where ");

        whereCondition.forEach(
                (key, value) -> {
                    stringBuilder.append(key);
                    if (value instanceof String) {
                        stringBuilder.append(" = ").append(value);
                    } else if (value instanceof List) {
                        stringBuilder.append(" in (");
                        ((List<String>) value).forEach(
                                item -> stringBuilder.append(item).append(", ")
                        );
                        stringBuilder.setLength(stringBuilder.length() - 2);
                        stringBuilder.append(")");
                    }
                    stringBuilder.append(" and ");
                }
        );

        stringBuilder.setLength(stringBuilder.length() - 5);
    }
}
