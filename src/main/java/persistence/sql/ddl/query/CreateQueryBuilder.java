package persistence.sql.ddl.query;

import persistence.sql.ddl.PrimaryKey;
import persistence.sql.ddl.SchemaExtractor;
import persistence.sql.ddl.TableColumn;
import persistence.sql.ddl.TableInfo;

import java.util.List;

public class CreateQueryBuilder implements QueryBuilder {

    private final SchemaExtractor schemaExtractor;
    private final List<PrimaryKeyGenerationStrategy> pkGenerationStrategies = List.of(
            new AutoKeyGenerationStrategy(),
            new IdentityKeyGenerationStrategy()
    );

    public CreateQueryBuilder() {
        this(new SchemaExtractor());
    }

    public CreateQueryBuilder(SchemaExtractor schemaExtractor) {
        this.schemaExtractor = schemaExtractor;
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
        TableInfo tableInfo = schemaExtractor.extract(entityClazz);
        PrimaryKeyGenerationStrategy pkGenerationStrategy = pkGenerationStrategies.stream()
                .filter(strategy -> strategy.supports(tableInfo.primaryKey()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Primary key generation strategy not found"));

        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE ").append(tableInfo.tableName());
        query.append(" (");

        for (TableColumn column : tableInfo.columns()) {
            column.applyToQuery(query, pkGenerationStrategy);
        }

        definePrimaryKey(tableInfo.primaryKey(), query);

        query.append(");");
        return query.toString();
    }

    private void definePrimaryKey(PrimaryKey pk, StringBuilder query) {
        query.append("PRIMARY KEY (").append(pk.name()).append(")");
    }
}
