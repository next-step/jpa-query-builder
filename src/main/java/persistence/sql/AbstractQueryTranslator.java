package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;
import persistence.exception.UnsupportedClassException;

public abstract class AbstractQueryTranslator {

    protected AbstractQueryTranslator() {

    }

    /**
     * Get the stream of fields that are not annotated with <code>@Transient</code> from the entity class
     * @param entityClass Entity class
     * @return Stream of fields that are annotated with <code>@Column</code> or <code>@Id</code>
     * @see Column
     * @see Id
     * @see Transient
     */
    protected Stream<Field> getColumnFieldStream(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1));
    }

    /**
     * Get the column name from the field
     * @param field Field
     * @return Column name from the field
     */
    protected String getColumnNameFrom(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }

        Column column = field.getAnnotation(Column.class);

        if (column.name().isEmpty()) {
            return field.getName();
        }

        return column.name();
    }

    /**
     * Get the column value from the object
     * @param columnValue Column value object
     * @return Column value from the object
     */
    protected String getColumnValueFromObject(Object columnValue) {
        // TODO: remove this else-if statement
        if (columnValue.getClass().equals(Boolean.class)) {
            return columnValue == Boolean.TRUE ? "1" : "0";
        } else if (columnValue.getClass().equals(String.class)) {
            return String.format("'%s'", columnValue);
        } else if (columnValue.getClass().equals(Integer.class)) {
            return columnValue.toString();
        } else if (columnValue.getClass().equals(Long.class)) {
            return columnValue.toString();
        }

        throw new UnsupportedClassException(columnValue.getClass());
    }
}
