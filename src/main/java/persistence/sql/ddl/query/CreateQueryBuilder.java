package persistence.sql.ddl.query;

import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.Dialect;
import persistence.sql.ddl.Queryable;
import persistence.sql.ddl.definition.TableDefinition;
import persistence.sql.ddl.definition.TableId;

public class CreateQueryBuilder implements DdlQueryBuilder {
    private final Dialect dialect;

    public CreateQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String build(Class<?> entityClazz) {
        TableDefinition tableDefinition = new TableDefinition(entityClazz);
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE ").append(tableDefinition.tableName());
        query.append(" (");

        for (Queryable column : tableDefinition.queryableColumns()) {
            column.apply(query, dialect);
        }

        definePrimaryKey(tableDefinition.tableId(), query);

        query.append(");");
        return query.toString();
    }

    private void definePrimaryKey(TableId pk, StringBuilder query) {
        query.append("PRIMARY KEY (").append(pk.name()).append(")");
    }
}