package persistence.sql.ddl.query;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.definition.TableDefinition;

public class DropQueryBuilder implements QueryBuilder {

    @Override
    public String build(Class<?> entityClass) {
        TableDefinition tableDefinition = new TableDefinition(entityClass);
        return "DROP TABLE " + tableDefinition.tableName() + " if exists;";
    }
}
