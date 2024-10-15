package persistence.sql.dml.query;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.definition.TableDefinition;

public class SelectAllQueryBuilder implements QueryBuilder {

    @Override
    public String build(Class<?> entityClass) {
        final TableDefinition tableDefinition = new TableDefinition(entityClass);

        return "SELECT * FROM " + tableDefinition.tableName() + ";";
    }
}
