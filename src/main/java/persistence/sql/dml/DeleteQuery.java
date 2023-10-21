package persistence.sql.dml;

import persistence.sql.common.instance.InstanceManager;
import persistence.sql.common.meta.EntityMeta;

public class DeleteQuery {

    private static final String DEFAULT_DELETE_QUERY = "DELETE FROM %s";

    private final EntityMeta entityMeta;
    private final InstanceManager instanceManager;
    private final Object arg;

    private <T> DeleteQuery(T t, Object arg) {
        this.entityMeta = EntityMeta.of(t.getClass());
        this.instanceManager = InstanceManager.of(t);
        this.arg = arg;
    }

    public static <T> String create(T t, Object arg) {
        return new DeleteQuery(t, arg).combine();
    }

    private String combine() {
        return String.join(" ",  getTableQuery(), getCondition());
    }

    private String getTableQuery() {
        return String.format(DEFAULT_DELETE_QUERY, entityMeta.getTableName());
    }

    private String getCondition() {
        return ConditionBuilder.getCondition(entityMeta.getIdName(), arg);
    }
}
