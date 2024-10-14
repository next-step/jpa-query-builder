package persistence.sql.dml;

import persistence.sql.meta.EntityTable;

public class DeleteQueryBuilder {
    private static final String QUERY_TEMPLATE = "DELETE FROM %s WHERE %s";

    private final EntityTable entityTable;
    private final Object entity;

    public DeleteQueryBuilder(Object entity) {
        this.entityTable = new EntityTable(entity.getClass());
        this.entity = entity;
    }

    public String delete() {
        final Object id = entityTable.getIdValue(entity);
        return entityTable.getQuery(QUERY_TEMPLATE, entityTable.getTableName(), entityTable.getWhereClause(id));
    }
}
