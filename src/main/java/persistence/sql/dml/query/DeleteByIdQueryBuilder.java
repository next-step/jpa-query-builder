package persistence.sql.dml.query;

import persistence.sql.definition.TableDefinition;

public class DeleteByIdQueryBuilder {

    public String build(Class<?> entityClass) {
        final TableDefinition tableDefinition = new TableDefinition(entityClass);
        return "DELETE FROM " + tableDefinition.tableName() + " WHERE id = ?;";
    }
}
