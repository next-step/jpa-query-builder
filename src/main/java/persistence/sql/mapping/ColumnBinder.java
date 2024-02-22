package persistence.sql.mapping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.QueryException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ColumnBinder {

    private final ColumnTypeMapper columnTypeMapper;

    public ColumnBinder(ColumnTypeMapper columnTypeMapper) {
        this.columnTypeMapper = columnTypeMapper;
    }

    public List<Column> createColumns(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(this::createColumn)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Column> createColumns(final Object object) {
        final Class<?> clazz = object.getClass();

        return Arrays.stream(clazz.getDeclaredFields())
                .map(field -> createColumn(field, object))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Column createColumn(final Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return null;
        }

        final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);
        final String columnName = toColumnName(field, columnAnnotation);
        final int sqlType = columnTypeMapper.toSqlType(field.getType());

        int length = 255;
        boolean nullable = true;
        boolean unique = false;

        if (columnAnnotation != null) {
            length = columnAnnotation.length();
            nullable = columnAnnotation.nullable();
            unique = columnAnnotation.unique();
        }

        final Column column = new Column(columnName, sqlType, new Value(field.getClass(), sqlType), length, nullable, unique);

        final Id idAnnotation = field.getAnnotation(Id.class);

        if (idAnnotation != null) {
            column.setPk(true);

            final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue != null) {
                column.setStrategy(generatedValue.strategy());
            }
        }

        return column;
    }

    private Column createColumn(final Field field, final Object object) {
        final Column column = createColumn(field);

        if (Objects.isNull(column)) {
            return null;
        }

        setColumnValue(column, field, object);

        return column;
    }

    private String toColumnName(final Field field, final jakarta.persistence.Column columnAnnotation) {
        if (columnAnnotation == null || columnAnnotation.name().isBlank()) {
            return field.getName();
        }

        return columnAnnotation.name();
    }

    private void setColumnValue(final Column column, final Field field, final Object object) {
        try {
            field.setAccessible(true);
            final Object value = field.get(object);
            column.setValue(value);
        } catch (IllegalAccessException e) {
            throw new QueryException(column.getName() + " column set value exception");
        }

    }

}
