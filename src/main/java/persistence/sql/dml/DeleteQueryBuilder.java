package persistence.sql.dml;

import persistence.sql.meta.ColumnMeta;
import persistence.sql.meta.EntityMeta;
import persistence.sql.util.StringConstant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DeleteQueryBuilder {

    private static final String DELETE = "DELETE";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";

    public String getDeleteAllQuery(Class<?> clazz) {
        validateEntityAnnotation(clazz);
        return new StringBuilder()
                .append(getDeleteHeaderQuery(clazz))
                .append(";")
                .toString();
    }

    public String getDeleteByPkQuery(Class<?> clazz, Object pkObject) {
        validateEntityAnnotation(clazz);
        return new StringBuilder()
                .append(getDeleteHeaderQuery(clazz))
                .append(WHERE)
                .append(getPkWhereClause(clazz.getDeclaredFields(), pkObject))
                .append(";")
                .toString();
    }

    private void validateEntityAnnotation(Class<?> clazz) {
        if (!EntityMeta.isEntity(clazz)) {
            throw new IllegalArgumentException("Delete Query 빌드 대상이 아닙니다.");
        }
    }

    private String getDeleteHeaderQuery(Class<?> clazz) {
        return new StringBuilder()
                .append(DELETE)
                .append(FROM)
                .append(EntityMeta.getTableName(clazz))
                .toString();
    }

    private String getPkWhereClause(Field[] fields, Object pkObject) {
        List<String> valueConditions = Arrays.stream(fields)
                .filter(ColumnMeta::isId)
                .map(field -> getValueCondition(field, pkObject))
                .collect(Collectors.toList());
        return String.join(AND, valueConditions);
    }

    private String getValueCondition(Field field, Object pkObject) {
        return ColumnMeta.getColumnName(field) + StringConstant.EQUAL + ColumnMeta.parseColumnValue(pkObject);
    }
}
