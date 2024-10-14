package persistence.sql.ddl.create.component;

public class DdlCreateQueryBuilder {
    private final StringBuilder query = new StringBuilder();

    private DdlCreateQueryBuilder() {
        this.query
                .append("CREATE TABLE {TABLE_NAME} (\n");
    }

    public static DdlCreateQueryBuilder newInstance() {
        return new DdlCreateQueryBuilder();
    }

    public DdlCreateQueryBuilder add(ComponentBuilder componentBuilder) {
        this.query
                .append(componentBuilder.getComponentBuilder())
                .append(",\n");
        return this;
    }

    public String build(String tableName) {
        query.setLength(query.length() - 3);
        return query.append("\n);").toString().replace("{TABLE_NAME}", tableName.toLowerCase());
    }
}
