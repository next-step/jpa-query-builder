package persistence.sql.dml.query;

import persistence.sql.definition.TableDefinition;
import persistence.sql.definition.TableId;

public class DeleteByIdQueryBuilder {

    public String build(Object entity) {
        final TableDefinition tableDefinition = new TableDefinition(entity.getClass());
        final TableId tableId = tableDefinition.tableId();
        final Object idValue = tableId.getValue(entity);
        final StringBuilder query = new StringBuilder("DELETE FROM ");

        query.append(tableDefinition.tableName());
        query.append(" WHERE id = ");
        query.append(idValue).append(";");

        return query.toString();
    }
}
