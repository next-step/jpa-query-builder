package persistence.sql.dml.h2;

public final class H2FindByIdQuery {
    private H2FindByIdQuery() {}

    public static String build(Class<?> clazz, Object id) {
        return new StringBuilder()
                .append(H2FindAllQuery.build(clazz))
                .append(H2WhereIdQuery.build(clazz, id))
                .toString();
    }
}
