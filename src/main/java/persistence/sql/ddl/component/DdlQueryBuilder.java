package persistence.sql.ddl.component;

public class DdlQueryBuilder {
    private final StringBuilder query = new StringBuilder();

    public DdlQueryBuilder() {
        this.query
                .append("CREATE TABLE {TABLE_NAME} (\n");
    }

    public static DdlQueryBuilder newInstance() {
        return new DdlQueryBuilder();
    }

    public DdlQueryBuilder add(ComponentBuilder componentBuilder) {
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
