package persistence.sql.mapping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.QueryException;
import persistence.sql.dml.QueryNumberValueBinder;
import persistence.sql.dml.QueryStringValueBinder;
import persistence.sql.dml.QueryValueBinder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ColumnBinder {

    private final ColumnTypeMapper columnTypeMapper;

    private final List<QueryValueBinder> queryValueBinders = initQueryValueBinders();

    public ColumnBinder(ColumnTypeMapper columnTypeMapper) {
        this.columnTypeMapper = columnTypeMapper;
    }

    private List<QueryValueBinder> initQueryValueBinders() {
        return List.of(
                new QueryStringValueBinder(),
                new QueryNumberValueBinder()
        );
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

        final String columnName = toColumnName(field);
        final int sqlType = columnTypeMapper.toSqlType(field.getType());

        final Column column = getColumn(field, columnName, sqlType);

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

    private Column getColumn(final Field field, final String columnName, final int sqlType) {
        int length = 255;
        boolean nullable = true;
        boolean unique = false;

        final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);
        if (columnAnnotation != null) {
            length = columnAnnotation.length();
            nullable = columnAnnotation.nullable();
            unique = columnAnnotation.unique();
        }

        return new Column(columnName, sqlType, new Value(field.getType(), sqlType), length, nullable, unique);
    }

    private Column createColumn(final Field field, final Object object) {
        final Column column = createColumn(field);

        if (Objects.isNull(column)) {
            return null;
        }

        setColumnValue(column, field, object);

        return column;
    }

    public static String toColumnName(final Field field) {
        final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (columnAnnotation == null || columnAnnotation.name().isBlank()) {
            return field.getName().toLowerCase();
        }

        return columnAnnotation.name();
    }

    private void setColumnValue(final Column column, final Field field, final Object object) {
        try {
            field.setAccessible(true);
            final Object value = field.get(object);

            final Value valueObject = column.getValue();
            valueObject.setValue(value);
            valueObject.setValueClause(valueClause(valueObject));
        } catch (IllegalAccessException e) {
            throw new QueryException(column.getName() + " column set value exception");
        }

    }

    private String valueClause(final Value value) {

        return queryValueBinders.stream()
                .filter(binder -> binder.support(value))
                .findFirst()
                .orElseThrow(() -> new QueryException("not found InsertQueryValueBinder for " + value.getOriginalType() + " type"))
                .bind(value.getValue());
    }

}
