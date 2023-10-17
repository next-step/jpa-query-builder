package persistence.sql.ddl;

import persistence.sql.meta.ColumnMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static persistence.sql.util.StringConstant.BLANK;
import static persistence.sql.util.StringConstant.COLUMN_JOIN;

public abstract class ColumnBuilder {

    public String getColumnDefinition(Field[] fields) {
        return String.join(COLUMN_JOIN, toSql(fields));
    }

    private List<String> toSql(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !ColumnMeta.isTransient(field))
                .map(this::toSql)
                .collect(Collectors.toList());
    }

    private String toSql(Field field) {
        return new StringBuilder()
                .append(ColumnMeta.getColumnName(field))
                .append(BLANK)
                .append(getSqlType(field.getType()))
                .append(getGenerationStrategy(field))
                .append(getPrimaryKey(field))
                .append(getNullable(field))
                .toString();
    }

    protected abstract String getSqlType(Class<?> type);

    protected abstract String getGenerationStrategy(Field field);

    protected abstract String getPrimaryKey(Field field);

    protected abstract String getNullable(Field field);

}
