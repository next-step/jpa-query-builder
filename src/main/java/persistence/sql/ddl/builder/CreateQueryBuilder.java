package persistence.sql.ddl.builder;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.generator.ColumnGenerator;
import persistence.sql.ddl.generator.ColumnGeneratorFactory;
import persistence.sql.meta.TableName;

public class CreateQueryBuilder implements QueryBuilder {
    private final Dialect dialect;

    public CreateQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String generateSQL(final Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(getTableName(clazz));
        sb.append("\n(\n");
        sb.append(getColumnSQL(clazz));
        sb.append("\n);\n");
        return sb.toString();
    }

    private String getTableName(final Class<?> clazz) {
        return new TableName(clazz).getTableName();
    }

    private String getColumnSQL(final Class<?> clazz) {
        ColumnGenerator columnGenerator = ColumnGeneratorFactory.getColumnGenerator(dialect, clazz.getDeclaredFields());
        return columnGenerator.generateColumns();
    }
}
