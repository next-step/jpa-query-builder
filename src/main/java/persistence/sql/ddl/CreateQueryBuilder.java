package persistence.sql.ddl;

public class CreateQueryBuilder implements QueryBuilder {

    private final SchemaExtractor schemaExtractor;

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
                case "Integer" -> "INT";
                default -> throw new IllegalArgumentException("Unknown type: " + type);
            };
        }
    }

    @Override
    public String build(Object entity) {
        EntityInfo entityInfo = schemaExtractor.extract(entity);
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE ").append(entityInfo.tableName());
        query.append(" (");

        for (FieldInfo field : entityInfo.fields()) {
            addColumn(field, query);
            query.append(", ");
        }

        String primaryKey = entityInfo.primaryKey().name();
        query.append("PRIMARY KEY (").append(primaryKey).append(")");

        query.append(");");
        return query.toString();
    }

    private static void addColumn(FieldInfo field, StringBuilder query) {
        String sqlType = SQLTypeTranslator.translate(field.type());
        query.append(field.name()).append(" ").append(sqlType);
    }
}
