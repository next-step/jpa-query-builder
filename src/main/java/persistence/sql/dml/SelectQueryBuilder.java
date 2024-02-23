package persistence.sql.dml;

import persistence.sql.column.Column;
import persistence.sql.column.Columns;
import persistence.sql.column.TableColumn;

public class SelectQueryBuilder {
    private static final String SELECT_QUERY_FORMAT = "select %s from %s";
    private static final String WHERE_CLAUSE_FORMAT = " where %s = %d";

    private final TableColumn tableColumn;
    private Columns columns;
    private String query;

    public SelectQueryBuilder (TableColumn tableColumn) {
        this(tableColumn, null);
    }

    private SelectQueryBuilder(TableColumn tableColumn, String query) {
        this.tableColumn = tableColumn;
        this.query = query;
    }

    public SelectQueryBuilder build(Object entity) {
        this.columns = Columns.of(entity.getClass().getDeclaredFields(), tableColumn.getDatabase().createDialect());
        this.query = String.format(SELECT_QUERY_FORMAT, columns.getColumnNames(), tableColumn.getName());
        return this;
    }

    public String findById(Long id) {
        return this.query + whereClause(id);
    }

    public String findAll() {
        return this.query;
    }

    private String whereClause(Long id) {
        Column pkColumn = columns.getPkColumn();
        return String.format(WHERE_CLAUSE_FORMAT, pkColumn.getColumnName(), id);
    }

}
