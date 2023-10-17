package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import persistence.sql.util.StringUtils;

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
                .filter(field -> !isTransient(field))
                .map(this::toSql)
                .collect(Collectors.toList());
    }

    private static boolean isTransient(Field field) {
        return field.getDeclaredAnnotation(Transient.class) != null;
    }

    private String toSql(Field field) {
        return new StringBuilder()
                .append(getColumnName(field))
                .append(BLANK)
                .append(getSqlType(field.getType()))
                .append(getGenerationStrategy(field))
                .append(getPrimaryKey(field))
                .append(getNullable(field))
                .toString();
    }

    private static String getColumnName(Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null || StringUtils.isNullOrEmpty(columnAnnotation.name())) {
            return field.getName().toLowerCase();
        }
        return columnAnnotation.name();
    }

    protected abstract String getSqlType(Class<?> type);

    protected abstract String getGenerationStrategy(Field field);

    protected abstract String getPrimaryKey(Field field);

    protected abstract String getNullable(Field field);

}
