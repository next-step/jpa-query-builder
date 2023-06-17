package persistence.sql.dml.h2;

import jakarta.persistence.Id;
import persistence.sql.exception.IdNameNotFoundException;
import persistence.sql.util.ColumnValue;

import java.util.Arrays;

public final class H2WhereIdQuery {
    private H2WhereIdQuery() {}

    public static String build(Class<?> clazz, Object id) {
        return new StringBuilder()
                .append(" WHERE ")
                .append(getIdName(clazz))
                .append(" = ")
                .append(ColumnValue.render(id))
                .toString();
    }

    private static String getIdName(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findAny()
                .orElseThrow(() -> new IdNameNotFoundException())
                .getName();
    }
}
