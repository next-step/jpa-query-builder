package persistence.sql.dml;

import persistence.sql.dialect.Dialect;
import persistence.sql.mapping.Column;
import persistence.sql.mapping.Table;
import persistence.sql.mapping.Value;

import java.util.List;
import java.util.stream.Collectors;

public class Insert {

    private final Table table;

    private final List<Column> columns;

    private final List<Column> pkColumns;

    public Insert(final Table table) {
        this.table = table;
        final List<Column> tempColumns = table.getColumns();

        this.columns = tempColumns.stream().filter(Column::isNotPk).collect(Collectors.toList());
        this.pkColumns = tempColumns.stream().filter(Column::isPk).collect(Collectors.toList());
    }

    public Table getTable() {
        return this.table;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public List<Column> getPkColumns() {
        return this.pkColumns;
    }

    public String columnNameClause(final Dialect dialect) {
        final String columnNameClause = columns.stream()
                .map(Column::getName)
                .collect(Collectors.joining(", "));

        final StringBuilder clause = new StringBuilder()
                .append(columnNameClause);

        if (clause.length() > 0) {
            clause.append(", ");
        }

        final String pkColumnNameClause = pkColumns.stream()
                .filter(column -> isPkWithValueClause(column, dialect))
                .map(Column::getName)
                .collect(Collectors.joining(", "));

        clause.append(pkColumnNameClause);

        return clause.toString();
    }

    public String columnValueClause(final Dialect dialect) {
        final String columnNameClause = columns.stream()
                .map(Column::getValue)
                .map(Value::getValueClause)
                .collect(Collectors.joining(", "));

        final StringBuilder clause = new StringBuilder()
                .append(columnNameClause);

        if (clause.length() > 0) {
            clause.append(", ");
        }

        final String pkColumnNameClause = pkColumns.stream()
                .filter(column -> isPkWithValueClause(column, dialect))
                .map(column -> getPkValueClause(column, dialect))
                .collect(Collectors.joining(", "));

        clause.append(pkColumnNameClause);

        return clause.toString();
    }

    private boolean isPkWithValueClause(final Column column, final Dialect dialect) {
        return !column.isIdentifierKey() || dialect.getIdentityColumnSupport().hasIdentityInsertKeyword();
    }

    private String getPkValueClause(final Column column, final Dialect dialect) {
        if (column.isIdentifierKey() && dialect.getIdentityColumnSupport().hasIdentityInsertKeyword()) {
            return dialect.getIdentityColumnSupport().getIdentityInsertString();
        }

        return column.getValue().getValueClause();
    }

}
