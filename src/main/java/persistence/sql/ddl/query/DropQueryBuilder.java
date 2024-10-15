package persistence.sql.ddl.query;

import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.definition.TableDefinition;

public class DropQueryBuilder implements DdlQueryBuilder {

    @Override
    public String build(Class<?> entityClazz) {
        TableDefinition tableDefinition = new TableDefinition(entityClazz);
        return "DROP TABLE " + tableDefinition.tableName() + " if exists;";
    }
}
