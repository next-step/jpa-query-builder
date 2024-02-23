package persistence.sql.dml;

import persistence.sql.column.Column;
import persistence.sql.column.Columns;
import persistence.sql.column.TableColumn;


public class DeleteQueryBuilder {

    private static final String DELETE_QUERY_FORMAT = "delete from %s";
    private static final String WHERE_CLAUSE_FORMAT = " where %s = %d";

    private final TableColumn tableColumn;
    private Columns columns;
    private String query;

    public DeleteQueryBuilder(TableColumn tableColumn) {
        this.tableColumn = tableColumn;
    }

    public DeleteQueryBuilder build(Object entity) {
        this.columns = Columns.of(entity.getClass().getDeclaredFields(), tableColumn.getDatabase().createDialect());
        this.query = String.format(DELETE_QUERY_FORMAT, tableColumn.getName());
        return this;
    }

    public String deleteById(Long id) {
        Column pkColumn = columns.getPkColumn();
        return query + WHERE_CLAUSE_FORMAT.formatted(pkColumn.getColumnName(), id);
    }
}
