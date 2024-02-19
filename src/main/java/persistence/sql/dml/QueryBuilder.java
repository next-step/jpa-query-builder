package persistence.sql.dml;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static persistence.sql.dml.parser.ValueParser.insertValuesClauseParse;
import static persistence.sql.dml.parser.ValueParser.valueParse;

public class QueryBuilder {

    public String createInsertQuery(Object object) {
        String tableName = createTableName(object.getClass());
        String columns = columnsClause(object.getClass());
        String values = valueClause(object);

        return String.format("insert into %s (%s) values (%s)", tableName, columns, values);
    }

    public String createFindAllQuery(final Class<?> clazz) {
        return String.format("select %s from %s", columnsClause(clazz), createTableName(clazz));
    }

    public String createFindByIdQuery(final Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(createFindAllQuery(object.getClass()));
        stringBuilder.append(" where ");

        final Field primaryField = getPrimaryField(object);
        stringBuilder.append(String.format("%s = %s", primaryField.getName(), valueParse(primaryField, object)));
        return stringBuilder.toString();
    }

    private Field getPrimaryField(final Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private String createTableName(Class<?> personClass) {
        if (!personClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException();
        }

        if (personClass.isAnnotationPresent(Table.class)) {
            return personClass.getAnnotation(Table.class).name();
        }

        return personClass.getSimpleName().toLowerCase();
    }

    private String columnsClause(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
            .sorted(Comparator.comparing(this::idFirstOrdered))
            .filter(this::isNotTransientField)
            .map(this::getFieldName)
            .collect(Collectors.joining(", "));
    }

    private String getFieldName(final Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isBlank()) {
            return field.getAnnotation(Column.class).name();
        }

        return field.getName();
    }

    private String valueClause(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .sorted(Comparator.comparing(this::idFirstOrdered))
                .filter(this::isNotTransientField)
                .map(f -> insertValuesClauseParse(f, object))
                .collect(Collectors.joining(", "));
    }

    private Integer idFirstOrdered(Field field) {
        return field.isAnnotationPresent(Id.class) ? 0 : 1;
    }

    private boolean isNotTransientField(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }
}
