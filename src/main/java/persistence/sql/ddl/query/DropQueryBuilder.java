package persistence.sql.ddl.query;

import persistence.sql.definition.TableDefinition;

public class DropQueryBuilder {

    public String build(Class<?> entityClass) {
        TableDefinition tableDefinition = new TableDefinition(entityClass);
        return "DROP TABLE " + tableDefinition.tableName() + " if exists;";
    }
}
