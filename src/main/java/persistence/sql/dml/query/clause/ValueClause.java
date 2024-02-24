package persistence.sql.dml.query.clause;

import persistence.sql.dml.query.exception.IllegalFieldValueException;
import persistence.sql.dml.query.exception.NotFoundFieldNameException;
import persistence.sql.entity.model.DomainTypes;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class ValueClause {
    private static final String FORMAT = "'%s'";
    private static final String DELIMITER = ",";

    private final List<String> values;

    public ValueClause(final List<String> values) {
        this.values = values;
    }

    public String toSql() {
        return String.join(DELIMITER, values);
    }

    public static ValueClause from(final Object instance, final DomainTypes domainTypes) {
        return new ValueClause(domainTypes.getDomainTypes()
                .stream()
                .map(domainType -> getValue(instance, getField(instance.getClass(), domainType.getName())))
                .collect(Collectors.toList()));
    }

    private static Field getField(final Class<?> clazz, final String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            throw new NotFoundFieldNameException();
        }
    }

    private static String getValue(final Object instance, final Field field) {
        try {
            field.setAccessible(true);
            if(field.getType() == String.class) {
                return String.format(FORMAT, field.get(instance));
            }
            return field.get(instance).toString();
        } catch (Exception e) {
            throw new IllegalFieldValueException();
        }
    }
}