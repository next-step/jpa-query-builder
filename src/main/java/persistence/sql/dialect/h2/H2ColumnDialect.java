package persistence.sql.dialect.h2;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.dialect.ColumnDialect;
import persistence.sql.dialect.TypeDialect;
import persistence.sql.util.ColumnName;

import java.lang.reflect.Field;

import static persistence.sql.util.StringConstant.BLANK;

public final class H2ColumnDialect implements ColumnDialect {
    private final TypeDialect typeDialect = H2TypeDialect.getInstance();

    private H2ColumnDialect() {}

    public static H2ColumnDialect getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public String getSqlColumn(Field field) {
        return new StringBuilder()
                .append(ColumnName.build(field))
                .append(" ")
                .append(getSqlType(field))
                .append(getNullable(field))
                .append(getGenerationStrategy(field))
                .append(getPKSql(field))
                .toString();
    }

    private String getSqlType(Field field) {
        return new StringBuilder()
                .append(typeDialect.getSqlType(field.getType()))
                .append(getColumnLength(field))
                .toString();
    }

    private String getColumnLength(Field field) {
        final Column column = field.getDeclaredAnnotation(Column.class);
        if (column == null || !field.getType().equals(String.class)) {
            return BLANK;
        }
        return new StringBuilder()
                .append("(")
                .append(column.length())
                .append(")")
                .toString();
    }

    private String getNullable(Field field) {
        final Column column = field.getDeclaredAnnotation(Column.class);
        return column == null || column.nullable()
                ? BLANK
                : " NOT NULL";
    }

    private String getPKSql(Field field) {
        final String PK_SQL = " PRIMARY KEY";
        return field.isAnnotationPresent(Id.class)
                ? PK_SQL
                : BLANK;
    }

    private String getGenerationStrategy(Field field) {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        return generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY
                ? " AUTO_INCREMENT"
                : BLANK;
    }

    private static class SingletonHelper {
        private static final H2ColumnDialect INSTANCE = new H2ColumnDialect();
    }
}
