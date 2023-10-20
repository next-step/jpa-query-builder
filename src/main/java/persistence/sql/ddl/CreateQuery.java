package persistence.sql.ddl;

import persistence.sql.common.meta.EntityManager;

class CreateQuery extends EntityManager {

    private static final String DEFAULT_CREATE_QUERY = "CREATE TABLE %s (%s)";
    private static final String DEFAULT_PRIMARY_KEY_QUERY = ", PRIMARY KEY (%s)";

    private <T> CreateQuery(Class<T> tClass) {
        super(tClass);
    }

    public static <T> String create(Class<T> tClass) {
        return new CreateQuery(tClass).join();
    }

    private String join() {
        return String.join(", ", combineQuery());
    }

    /**
     * 해당 Class를 분석하여 CREATE QUERY로 조합합니다.
     */
    private String combineQuery() {
        return String.format(DEFAULT_CREATE_QUERY, getTableName(), getConstraintsWithColumns() + createColumns());
    }

    private String createColumns() {
        return String.format(DEFAULT_PRIMARY_KEY_QUERY, getPrimaryKeyWithComma());
    }
}
