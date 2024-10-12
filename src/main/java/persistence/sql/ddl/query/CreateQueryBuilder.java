package persistence.sql.ddl.query;

import persistence.sql.ddl.EntityInfo;
import persistence.sql.ddl.FieldInfo;
import persistence.sql.ddl.PrimaryKey;
import persistence.sql.ddl.SchemaExtractor;

import java.util.List;
import java.util.Optional;

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

    static class SQLTypeTranslator {
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
        EntityInfo entityInfo = schemaExtractor.extract(entityClazz);
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE ").append(entityInfo.tableName());
        query.append(" (");

        for (FieldInfo field : entityInfo.fields()) {
            addColumn(field, query);
            query.append(", ");
        }

        addPrimaryKey(entityInfo, query);

        query.append(");");
        return query.toString();
    }

    private void addColumn(FieldInfo field, StringBuilder query) {
        String sqlType = SQLTypeTranslator.translate(field.type());
        query.append(field.name()).append(" ").append(sqlType);
        if (field.notNullable()) {
            query.append(" NOT NULL");
        }
        if (field.isPrimaryKey()) {
            PrimaryKey pk = field.primaryKey();
            Optional<PrimaryKeyGenerationStrategy> strategy = pkGenerationStrategies
                    .stream()
                    .filter(s -> s.supports(pk))
                    .findFirst();

            strategy.ifPresent(primaryKeyGenerationStrategy ->
                    query.append(" ").append(primaryKeyGenerationStrategy.generatePrimaryKeySQL(pk))
            );
        }
    }

    private void addPrimaryKey(EntityInfo entityInfo, StringBuilder query) {
        PrimaryKey pk = entityInfo.primaryKey();
        query.append("PRIMARY KEY (").append(pk.name()).append(")");
    }
}
