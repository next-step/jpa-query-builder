package persistence.sql.dml.query;

import persistence.sql.QueryBuilder;
import persistence.sql.definition.TableDefinition;

public class DeleteByIdQueryBuilder implements QueryBuilder {

    @Override
    public String build(Class<?> entityClass) {
        final TableDefinition tableDefinition = new TableDefinition(entityClass);
        return "DELETE FROM " + tableDefinition.tableName() + " WHERE id = ?;";
    }
}
