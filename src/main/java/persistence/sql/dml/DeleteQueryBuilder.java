package persistence.sql.dml;

import persistence.sql.meta.EntityField;
import persistence.sql.meta.EntityTable;

public class DeleteQueryBuilder {
    private static final String QUERY_TEMPLATE = "DELETE FROM %s WHERE %s";

    private final EntityTable entityTable;

    public DeleteQueryBuilder(Class<?> entityClass) {
        this.entityTable = new EntityTable(entityClass);
    }

    public String delete(Object entity) {
        final EntityField entityField = entityTable.getIdEntityField();
        final Object id = entityField.getValue(entity);
        return entityTable.getQuery(QUERY_TEMPLATE, entityTable.getTableName(), entityTable.getWhereClause(id));
    }
}
