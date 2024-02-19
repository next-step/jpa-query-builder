package persistence.sql.dml;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static persistence.sql.dml.ValueParser.parse;

public class QueryBuilder {

    public String createInsertQuery(Object object) {
        String tableName = createTableName(object.getClass());
        String columns = columnsClause(object.getClass());
        String values = valueClause(object);

        return String.format("insert into %s (%s) values (%s)", tableName, columns, values);
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
                .map(f -> parse(f, object))
                .collect(Collectors.joining(", "));
    }

    private Integer idFirstOrdered(Field field) {
        return field.isAnnotationPresent(Id.class) ? 0 : 1;
    }

    private boolean isNotTransientField(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }
}
