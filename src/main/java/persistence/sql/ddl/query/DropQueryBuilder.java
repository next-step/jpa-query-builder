package persistence.sql.ddl.query;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.definition.TableDefinition;

public class DropQueryBuilder implements QueryBuilder {

    @Override
    public String build(Class<?> entityClazz) {
        TableDefinition tableDefinition = new TableDefinition(entityClazz);
        return "DROP TABLE " + tableDefinition.tableName() + " if exists;";
    }
}
