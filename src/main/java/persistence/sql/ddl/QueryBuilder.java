package persistence.sql.ddl;

import persistence.sql.converter.TypeMapper;
import persistence.sql.dialect.Dialect;
import persistence.sql.converter.EntityConverter;
import persistence.sql.model.Table;

public class QueryBuilder {

    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSE_PARENTHESIS = ")";

    private final Dialect dialect;
    private final EntityConverter entityConverter;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.entityConverter = new EntityConverter(new TypeMapper());
    }

    public String buildCreateQuery(Class<?> clazz) {

        Table table = entityConverter.convertEntityToTable(clazz);

        StringBuilder statement = new StringBuilder(dialect.getCreateTableCommand()).append(" ");
        statement.append(table.getName());
        statement.append(OPEN_PARENTHESIS);
        statement.append(String.join(",", table.getColumnDefinitions(dialect)));
        statement.append(CLOSE_PARENTHESIS);

        return statement.toString();
    }

}
