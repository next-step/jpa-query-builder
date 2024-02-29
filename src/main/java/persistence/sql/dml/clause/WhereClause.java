package persistence.sql.dml.clause;

import jakarta.persistence.Transient;
import persistence.sql.ddl.column.ColumnName;
import util.ClauseUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class WhereClause {

    private WhereClause() {
    }

    public static String getWhereClause(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> {
                    field.setAccessible(true);
                    return Optional.ofNullable(getFieldValue(field, entity)).isPresent();
                })
                .map(field -> toWhereClause(entity, field))
                .collect(Collectors.joining(" AND "));
    }

    private static String toWhereClause(Object entity, Field field) {
        ColumnName columnName = ColumnName.from(field);
        String fieldValue = getFieldValue(field, entity).toString();

        return String.format("%s = %s", columnName.getName(), ClauseUtil.addQuotesWhenRequire(field.getType(), fieldValue));
    }

    public static Object getFieldValue(Field field, Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("not access %s field", field.getName()));
        }
    }
}
