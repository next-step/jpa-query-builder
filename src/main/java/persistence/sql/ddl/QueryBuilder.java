package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.model.Table;

public class QueryBuilder {

    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSE_PARENTHESIS = ")";

    private final Dialect dialect;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String buildCreateQuery(Table table) {
        StringBuilder statement = new StringBuilder(dialect.getCreateTableCommand()).append(" ");
        statement.append(table.getName());
        statement.append(OPEN_PARENTHESIS);
        statement.append(String.join(",", table.getColumnDefinitions(dialect)));
        statement.append(CLOSE_PARENTHESIS);

        return statement.toString();
    }

    public String buildDropQuery(Table table) {

        StringBuilder statement = new StringBuilder(dialect.getDropTableCommand()).append(" ");
        statement.append(table.getName());

        return statement.toString();
    }

}
