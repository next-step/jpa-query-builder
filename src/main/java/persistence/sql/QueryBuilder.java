package persistence.sql;

import persistence.sql.common.util.NameConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.data.QueryType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface QueryBuilder {
    String DELIMITER = ", ";
    Set<Class<?>> WRAPPER_TYPES = new HashSet<>(Arrays.asList(
            Boolean.class,
            Character.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Void.class
    ));

    QueryType queryType();

    boolean supported(QueryType queryType);

    String build(MetadataLoader<?> loader, Object value);


    default String getColumnNames(MetadataLoader<?> loader, NameConverter nameConverter) {
        return String.join(DELIMITER, loader.getColumnNameAll(nameConverter));
    }

    default String getWhereIdClause(MetadataLoader<?> loader, Object value) {
        Field pkField = loader.getPrimaryKeyField();
        String pkColumnName = loader.getColumnName(pkField);
        Object pkValue = extractValue(pkField, value);

        return "%s = %s".formatted(pkColumnName, toColumnValue(pkValue));
    }

    default Object extractValue(Field field, Object value) {
        try {
            if (isPrimitiveOrWrapper(value)) {
                return value;
            }

            field.setAccessible(true);
            return field.get(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isPrimitiveOrWrapper(Object value) {
        return value == null || value.getClass().isPrimitive() || WRAPPER_TYPES.contains(value.getClass());
    }

    default String toColumnValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value.toString();
    }
}
