package persistence.sql.ddl.builder;

import persistence.sql.ddl.clause.Create;
import persistence.sql.ddl.dialect.Dialect;

public class CreateQueryBuilder implements QueryBuilder {
    private final Dialect dialect;

    public CreateQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String generateSQL(final Class<?> clazz) {
        Create create = new Create(clazz, dialect);
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(create.getTableName());
        sb.append("\n(\n");
        sb.append(create.getColumns());
        sb.append("\n);\n");
        return sb.toString();
    }
}
