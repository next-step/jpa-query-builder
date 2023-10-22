package persistence.sql.dml.builder;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import persistence.sql.meta.ColumnMeta;
import persistence.sql.meta.EntityMeta;
import persistence.sql.util.StringConstant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class InsertQueryBuilder {

    private static final String INSERT_HEADER = "INSERT INTO ";
    private static final String VALUES = "VALUES";

    public String getQuery(Object object) {
        validateEntityAnnotation(object.getClass());
        return buildQuery(object);
    }

    private void validateEntityAnnotation(Class<?> clazz) {
        if (!EntityMeta.isEntity(clazz)) {
            throw new IllegalArgumentException("Insert Query 빌드 대상이 아닙니다.");
        }
    }

    private String buildQuery(Object object) {
        Map<String, String> insertKeyValueMap = buildInsertKeyValueMap(object);
        return new StringBuilder()
                .append(INSERT_HEADER)
                .append(EntityMeta.getTableName(object.getClass()))
                .append(" (")
                .append(columnsClause(insertKeyValueMap))
                .append(") ")
                .append(VALUES)
                .append(" (")
                .append(valuesClause(insertKeyValueMap))
                .append(");")
                .toString();
    }

    private Map<String, String> buildInsertKeyValueMap(Object object) {
        Map<String, String> insertKeyValueMap = new LinkedHashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            put(insertKeyValueMap, object, field);
        }
        return insertKeyValueMap;
    }

    private void put(Map<String, String> insertKeyValueMap, Object object, Field field) {
        if (isInsertSkipTarget(field)) {
            return;
        }
        insertKeyValueMap.put(getColumnClause(field), getValueClause(object, field));
    }

    private boolean isInsertSkipTarget(Field field) {
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);
        if (generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY) {
            return true;
        }
        Transient transientAnnotation = field.getDeclaredAnnotation(Transient.class);
        return transientAnnotation != null;
    }

    private String getColumnClause(Field field) {
        return ColumnMeta.getColumnName(field);
    }

    private String getValueClause(Object object, Field field) {
        return ColumnMeta.getColumnValue(object, field);
    }

    private String columnsClause(Map<String, String> insertKeyValueMap) {
        return String.join(StringConstant.COLUMN_JOIN, insertKeyValueMap.keySet());
    }

    private String valuesClause(Map<String, String> insertKeyValueMap) {
        return String.join(StringConstant.COLUMN_JOIN, insertKeyValueMap.values());
    }
}
