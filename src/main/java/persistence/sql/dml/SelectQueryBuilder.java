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

    public String getSelectAllQuery(Class<?> clazz) {
        return new StringBuilder()
                .append(SELECT)
                .append(getColumnsClause(clazz.getDeclaredFields()))
                .append(FROM)
                .append(EntityMeta.getTableName(clazz))
                .append(";")
                .toString();
    }

    private String getColumnsClause(Field[] fields) {
        List<String> columnNames = Arrays.stream(fields)
                .map(ColumnMeta::getColumnName)
                .collect(Collectors.toList());
        return String.join(StringConstant.COLUMN_JOIN, columnNames);
    }
}
