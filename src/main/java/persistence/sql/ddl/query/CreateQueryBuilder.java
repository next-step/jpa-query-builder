package persistence.sql.ddl.query;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.definition.PrimaryKey;
import persistence.sql.ddl.Queryable;
import persistence.sql.ddl.definition.TableDefinition;

public class CreateQueryBuilder implements QueryBuilder {
    public CreateQueryBuilder() {
    }

    public static class SQLTypeTranslator {
        public static String translate(String type) {
            return switch (type) {
                case "Long" -> "BIGINT";
                case "String" -> "VARCHAR(255)";
                case "Integer" -> "INTEGER";
                default -> throw new IllegalArgumentException("Unknown type: " + type);
            };
        }
    }

    @Override
    public String build(Class<?> entityClazz) {
        TableDefinition tableDefinition = new TableDefinition(entityClazz);
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE ").append(tableDefinition.tableName());
        query.append(" (");

        for (Queryable column : tableDefinition.queryableColumns()) {
            column.apply(query);
        }

        definePrimaryKey(tableDefinition.primaryKey(), query);

        query.append(");");
        return query.toString();
    }

    private void definePrimaryKey(PrimaryKey pk, StringBuilder query) {
        query.append("PRIMARY KEY (").append(pk.name()).append(")");
    }
}
