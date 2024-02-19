package persistence.sql.dml;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

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
                .map(field -> dataParser(field, object))
                .collect(Collectors.joining(", "));
    }

    private Integer idFirstOrdered(Field field) {
        return field.isAnnotationPresent(Id.class) ? 0 : 1;
    }

    private boolean isNotTransientField(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private String dataParser(Field field, Object object) {
        if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class)) {
            if (field.getAnnotation(GeneratedValue.class).strategy().equals(GenerationType.IDENTITY)) {
                return "default";
            }
        }
        field.setAccessible(true);
        Object value;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (field.getType().equals(String.class)) {
            return String.format("'%s'", value);
        }

        return (String) value;
    }
}
