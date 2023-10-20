package persistence.sql.ddl;

import persistence.sql.common.meta.EntityManager;

public class DropQuery extends EntityManager {
    private static final String DEFAULT_DROP_QUERY = "DROP TABLE %s";

    private <T> DropQuery(Class<T> tClass) {
        super(tClass);
    }

    public static <T> String drop(Class<T> tClass) {
        return new DropQuery(tClass).combineQuery();
    }

    private String combineQuery() {
        return String.format(DEFAULT_DROP_QUERY, getTableName());
    }
}
