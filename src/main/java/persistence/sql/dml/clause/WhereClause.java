package persistence.sql.dml.clause;

import jakarta.persistence.Transient;
import persistence.sql.ddl.column.ColumnName;
import util.ClauseUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class WhereClause {

    private final Object entity;

    public WhereClause(Object entity) {
        this.entity = entity;
    }

    public String getWhereClause() {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> {
                    field.setAccessible(true);
                    return Optional.ofNullable(getFieldValue(field)).isPresent();
                })
                .map(this::toWhereClause)
                .collect(Collectors.joining(" AND "));
    }

    private String toWhereClause(Field field) {
        ColumnName columnName = ColumnName.from(field);
        String fieldValue = getFieldValue(field).toString();

        return String.format("%s = %s", columnName.getName(), ClauseUtil.addQuotesWhenRequire(field.getType(), fieldValue));
    }

    private Object getFieldValue(Field field) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("not access %s field", field.getName()));
        }
    }
}
