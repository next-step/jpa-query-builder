package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.model.Column;
import persistence.sql.model.Table;

import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {

    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE";
    private static final String DROP_TABLE_COMMAND = "DROP TABLE";
    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSE_PARENTHESIS = ")";

    private final Dialect dialect;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String buildCreateQuery(Table table) {
        StringBuilder statement = new StringBuilder(CREATE_TABLE_COMMAND).append(" ");
        statement.append(table.getName());
        statement.append(OPEN_PARENTHESIS);
        statement.append(String.join(",", createColumnDefinitions(table.getColumns())));
        statement.append(CLOSE_PARENTHESIS);

        return statement.toString();
    }

    public String buildDropQuery(Table table) {

        StringBuilder statement = new StringBuilder(DROP_TABLE_COMMAND).append(" ");
        statement.append(table.getName());

        return statement.toString();
    }

    private List<String> createColumnDefinitions(List<Column> columns) {
        return columns.stream()
            .map(this::createColumnDefinition)
            .collect(Collectors.toList());
    }

    private String createColumnDefinition(Column column) {
        StringBuilder definition = new StringBuilder();
        definition.append(column.getName()).append(" ");
        definition.append(dialect.getTypeName(column.getType())).append(" ");
        definition.append(column.getNullableConstraint().getConstraintSql()).append(" ");
        definition.append(dialect.getGenerationStrategy(column.getGenerationType())).append(" ");
        definition.append(column.getPrimaryKeyConstraint().getConstraintSql()).append(" ");
        return definition.toString().trim();
    }

}
