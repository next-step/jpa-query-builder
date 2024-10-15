package persistence.sql.dml.query;

import persistence.sql.definition.TableDefinition;

public class SelectAllQueryBuilder {

    public String build(Class<?> entityClass) {
        final TableDefinition tableDefinition = new TableDefinition(entityClass);

        return "SELECT * FROM " + tableDefinition.tableName() + ";";
    }
}
