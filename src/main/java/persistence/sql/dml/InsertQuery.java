package persistence.sql.dml;

import persistence.sql.common.instance.InstanceManager;
import persistence.sql.common.meta.EntityManager;

public class InsertQuery extends EntityManager {
    private static final String DEFAULT_INSERT_COLUMN_QUERY = "INSERT INTO %s (%s)";
    private static final String DEFAULT_INSERT_VALUE_QUERY = "VALUES(%s)";

    private InstanceManager instanceManager;

    protected <T> InsertQuery(T t) {
        super(t);
        this.instanceManager = new InstanceManager(getTable(), t);
    }

    public static <T> String create(T t) {
        return new InsertQuery(t).combineQuery();
    }

    /**
     * 해당 Class를 분석하여 INSERT QUERY로 조합합니다.
     */
    private String combineQuery() {
        return String.join(" ", parseColumns(), parseValues());
    }

    private String parseColumns() {
        return String.format(DEFAULT_INSERT_COLUMN_QUERY, getTableName(), getColumnsWithComma());
    }

    private String parseValues() {
        return String.format(DEFAULT_INSERT_VALUE_QUERY, instanceManager.getValuesWithComma());
    }
}
