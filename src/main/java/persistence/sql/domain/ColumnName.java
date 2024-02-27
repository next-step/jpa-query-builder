package persistence.sql.domain;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnName {

    private final String javaFieldName;

    private final String jdbcColumnName;

    public ColumnName(Field field) {
        this.javaFieldName = field.getName();
        this.jdbcColumnName = getJdbcColumnName(field);
    }

    private String getJdbcColumnName(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation != null && annotation.name().length() > 0) {
            return annotation.name();
        }
        return field.getName();
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }

    public String getJdbcColumnName() {
        return jdbcColumnName;
    }
}
