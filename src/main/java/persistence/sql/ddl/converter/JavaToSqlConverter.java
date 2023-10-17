package persistence.sql.ddl.converter;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class JavaToSqlConverter {
    private final JavaToSqlMapper javaToSqlMapper;

    public JavaToSqlConverter(JavaToSqlMapper javaToSqlMapper) {
        this.javaToSqlMapper = javaToSqlMapper;
    }

    public List<String> convert(Field field) {
        return List.of(
                convertName(field),
                convertAttribute(field),
                convertGenerateValue(field),
                convertNullable(field)
        );
    }

    private String convertName(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .filter(column -> !column.name().isBlank())
                .map(Column::name)
                .orElse(field.getName());
    }

    private String convertNullable(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::nullable)
                .map(nullable -> nullable ? "" : "NOT NULL")
                .orElse("");
    }

    private String convertAttribute(Field field) {
        return javaToSqlMapper.convert(field.getType()) + extractLength(field);
    }

    private String convertGenerateValue(Field field) {
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

