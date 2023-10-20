package persistence.sql.dml;

import persistence.sql.common.meta.EntityManager;

public class DeleteQuery extends EntityManager {

    private static final String DEFAULT_DELETE_QUERY = "DELETE FROM %s";
    private final Object arg;

    private <T> DeleteQuery(Class<T> tClass, Object arg) {
        super(tClass);
        this.arg = arg;
    }

    public static <T> String create(Class<T> tClass, Object arg) {
        return new DeleteQuery(tClass, arg).combine();
    }

    private String combine() {
        return String.join(" ",  getTableQuery(), getCondition());
    }

    private String getTableQuery() {
        return String.format(DEFAULT_DELETE_QUERY, getTableName());
    }

    private String getCondition() {
        return ConditionBuilder.getCondition(getIdName(), arg);
    }
}
