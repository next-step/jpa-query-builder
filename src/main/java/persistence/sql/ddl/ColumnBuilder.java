package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static persistence.sql.util.StringConstant.BLANK;
import static persistence.sql.util.StringConstant.EMPTY_STRING;

public class ColumnBuilder {

    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String NOT_NULL = "NOT NULL";
    private static final String COLUMN_JOIN = ", ";

    public static String getColumnDefinition(Field[] fields) {
        return String.join(COLUMN_JOIN, toSql(fields));
    }

    private static List<String> toSql(Field[] fields) {
        return Arrays.stream(fields)
                .map(ColumnBuilder::toSql)
                .collect(Collectors.toList());
    }

    private static String toSql(Field field) {
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

    private static String getSqlType(Class<?> type) {
        return ColumnType.getSqlType(type);
    }

    private static String getGenerationStrategy(Field field) {
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);
        if (generatedValue == null) {
            return EMPTY_STRING;
        }
        if (generatedValue.strategy() == GenerationType.IDENTITY) {
            return BLANK + AUTO_INCREMENT;
        }
        return EMPTY_STRING;
    }

    private static String getPrimaryKey(Field field) {
        Id pkAnnotation = field.getDeclaredAnnotation(Id.class);
        if (pkAnnotation != null) {
            return BLANK + PRIMARY_KEY;
        }
        return EMPTY_STRING;
    }

    private static String getNullable(Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.nullable()) {
            return BLANK + NOT_NULL;
        }
        return EMPTY_STRING;
    }

}
