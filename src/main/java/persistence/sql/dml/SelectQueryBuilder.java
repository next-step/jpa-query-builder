package persistence.sql.dml;

import persistence.sql.meta.ColumnMeta;
import persistence.sql.meta.EntityMeta;
import persistence.sql.util.StringConstant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SelectQueryBuilder {

    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";

    public String getSelectAllQuery(Class<?> clazz) {
        validateEntityAnnotation(clazz);
        return new StringBuilder()
                .append(getSelectHeaderQuery(clazz))
                .append(";")
                .toString();
    }

    public String getSelectByPkQuery(Class<?> clazz, Object object) {
        validateEntityAnnotation(clazz);
        return new StringBuilder()
                .append(getSelectHeaderQuery(clazz))
                .append(WHERE)
                .append(getPkWhereClause(clazz.getDeclaredFields(), object))
                .append(";")
                .toString();
    }

    private void validateEntityAnnotation(Class<?> clazz) {
        if (!EntityMeta.isEntity(clazz)) {
            throw new IllegalArgumentException("Select Query 빌드 대상이 아닙니다.");
        }
    }

    private String getSelectHeaderQuery(Class<?> clazz) {
        return new StringBuilder()
                .append(SELECT)
                .append(getColumnsClause(clazz.getDeclaredFields()))
                .append(FROM)
                .append(EntityMeta.getTableName(clazz))
                .toString();
    }

    private String getColumnsClause(Field[] fields) {
        List<String> columnNames = Arrays.stream(fields)
                .filter(field -> !ColumnMeta.isTransient(field))
                .map(ColumnMeta::getColumnName)
                .collect(Collectors.toList());
        return String.join(StringConstant.COLUMN_JOIN, columnNames);
    }

    private String getPkWhereClause(Field[] fields, Object object) {
        List<String> valueConditions = Arrays.stream(fields)
                .filter(ColumnMeta::isId)
                .map(field -> getValueCondition(field, object))
                .collect(Collectors.toList());
        return String.join(AND, valueConditions);
    }

    private String getValueCondition(Field field, Object object) {
        return ColumnMeta.getColumnName(field) + StringConstant.EQUAL + ColumnMeta.getColumnValue(object, field);
    }
}
