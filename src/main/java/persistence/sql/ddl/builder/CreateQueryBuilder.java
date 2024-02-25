package persistence.sql.ddl.builder;

import persistence.sql.ddl.clause.DDLMeta;
import persistence.sql.ddl.dialect.Dialect;

public class CreateQueryBuilder implements QueryBuilder {
    private final Dialect dialect;

    public CreateQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String generateSQL(final Class<?> clazz) {
        DDLMeta ddlMeta = new DDLMeta(clazz, dialect);
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(ddlMeta.getTableName());
        sb.append("\n(\n");
        sb.append(ddlMeta.getColumns());
        sb.append("\n);\n");
        return sb.toString();
    }
}
