package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import persistence.sql.meta.EntityMeta;
import persistence.sql.util.StringConstant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class InsertQueryBuilder {

    private static final String INSERT_HEADER = "INSERT INTO ";
    private static final String VALUES = "VALUES";

    public String getQuery(Class<?> clazz) {
        validateEntityAnnotation(clazz);
        return buildQuery(clazz);
    }

    private void validateEntityAnnotation(Class<?> clazz) {
        if (!EntityMeta.isEntity(clazz)) {
            throw new IllegalArgumentException("Create Query 빌드 대상이 아닙니다.");
        }
    }

    private String buildQuery(Class<?> clazz) {
        Map<String, String> insertKeyValueMap = buildInsertKeyValueMap(clazz);
        return new StringBuilder()
                .append(INSERT_HEADER)
                .append(EntityMeta.getTableName(clazz))
                .append(" (")
                .append(columnsClause(insertKeyValueMap))
                .append(") ")
                .append(VALUES)
                .append(" (")
                .append(valuesClause(insertKeyValueMap))
                .append(");")
                .toString();
    }

    private Map<String, String> buildInsertKeyValueMap(Class<?> clazz) {
        Map<String, String> insertKeyValueMap = new LinkedHashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            put(insertKeyValueMap, field);
        }
        return insertKeyValueMap;
    }

    private void put(Map<String, String> insertKeyValueMap, Field field) {
        if (isInsertSkipTarget(field)) {
            return;
        }
        insertKeyValueMap.put(getColumnClause(field), getValueClause(field));
    }

    private boolean isInsertSkipTarget(Field field) {
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);
        if (generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY) {
            return true;
        }
        Transient transientAnnotation = field.getDeclaredAnnotation(Transient.class);
        if (transientAnnotation != null) {
            return true;
        }
        return false;
    }

    private String getColumnClause(Field field) {

        return null;
    }

    private String getValueClause(Field field) {

        return null;
    }

    private String columnsClause(Map<String, String> insertKeyValueMap) {
        return String.join(StringConstant.COLUMN_JOIN, insertKeyValueMap.keySet());
    }

    private String valuesClause(Map<String, String> insertKeyValueMap) {
        return String.join(StringConstant.COLUMN_JOIN, insertKeyValueMap.values());
    }
}
