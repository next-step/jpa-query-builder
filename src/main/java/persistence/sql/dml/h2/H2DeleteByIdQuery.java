package persistence.sql.dml.h2;

import persistence.sql.util.TableName;

public final class H2DeleteByIdQuery {
    private H2DeleteByIdQuery() {}

    public static String build(Class<?> clazz, Object id) {
        return new StringBuilder()
                .append("DELETE FROM ")
                .append(TableName.build(clazz))
                .append(H2WhereIdQuery.build(clazz, id))
                .toString();
    }
}
