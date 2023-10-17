package persistence.sql.ddl.builder;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import java.lang.reflect.Field;
import java.util.Optional;

public class JavaToSqlConverter {
    private final JavaToSqlMapper javaToSqlMapper;

    public JavaToSqlConverter(JavaToSqlMapper javaToSqlMapper) {
        this.javaToSqlMapper = javaToSqlMapper;
    }

    protected String convertName(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .filter(column -> !column.name().isBlank())
                .map(Column::name)
                .orElse(field.getName());
    }

    protected String convertNullable(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::nullable)
                .map(nullable -> nullable ? "" : "NOT NULL")
                .orElse("");
    }

    protected String convertAttribute(Field field) {
        return javaToSqlMapper.convert(field.getType()) + extractLength(field);
    }

    protected String convertGenerateValue(Field field) {
        return Optional.ofNullable(field.getAnnotation(GeneratedValue.class))
                .map(generateValue -> javaToSqlMapper.convert(generateValue.strategy().getClass()))
                .orElse("");
    }

    private String extractLength(Field field) {
        if (!field.getType().equals(String.class)) {
            return "";
        }
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .map(column -> "(" + column.length() + ")")
                .orElse("");
    }
}

