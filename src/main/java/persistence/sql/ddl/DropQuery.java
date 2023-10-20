package persistence.sql.ddl;

import persistence.sql.common.Table;

public class DropQuery extends Table {
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
