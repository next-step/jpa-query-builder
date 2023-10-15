package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateDDLQueryBuilder extends DDLQueryBuilder {

    public static <T> String of(Class<T> tClass) {
        validator.validate(tClass);
        return String.format("CREATE TABLE %s ( %s );", prepareTableStatement(tClass), prepareColumnStatements(tClass));
    }

    private static String prepareColumnStatements(Class<?> tClass) {
        List<String> statements = new ArrayList<>();

        Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Column.class))
                .forEach(field -> statements.add(prepareStatement(field)));

        return String.join(", ", statements);
    }

    private static String prepareStatement(Field field) {
        List<String> components = Arrays.asList(
                getName(field),
                javaToSqlTypeMapper.convert(field.getType()) + getSize(field),
                getGenerateValue(field),
                getNullable(field)
        );

        return components.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "));
    }


    private static String getName(Field field) {
        String columnName = getAnnotationValue(field, Column.class, Column::name, field.getName());
        return columnName.isBlank() ? field.getName() : columnName;
    }

    private static String getNullable(Field field) {
        return getAnnotationValue(field, Column.class, Column::nullable, true) ? "" : "NOT NULL";
    }

    private static String getGenerateValue(Field field) {
        return getAnnotationValue(field, GeneratedValue.class,
                (generatedValue) -> javaToSqlTypeMapper.convert(generatedValue.strategy().getClass()), "");
    }

    private static String getSize(Field field) {
        if (field.getType().equals(String.class)) {
            return "(" + getAnnotationValue(field, Column.class, Column::length, 255) + ")";
        }

        return getAnnotationValue(field, Column.class, Column::scale, 0) == 0 ? ""
                : "(" + getAnnotationValue(field, Column.class, Column::scale, 0) + ")";
    }
}