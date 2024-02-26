package persistence.sql.dml;

import persistence.sql.column.Columns;
import persistence.sql.column.IdColumn;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Dialect;

public class SelectQueryBuilder {
    private static final String SELECT_QUERY_FORMAT = "select %s, %s from %s";
    private static final String WHERE_CLAUSE_FORMAT = " where %s = %d";

    private TableColumn tableColumn;
    private Columns columns;
    private IdColumn idColumn;
    private final Dialect dialect;

    public SelectQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public SelectQueryBuilder build(Class<?> entity) {
        this.tableColumn = new TableColumn(entity);
        this.columns = new Columns(entity.getDeclaredFields(), dialect);
        this.idColumn = new IdColumn(entity.getDeclaredFields(), dialect);
        return this;
    }

    public String findById(Long id) {
        return String.format(SELECT_QUERY_FORMAT, idColumn.getName(), columns.getColumnNames(),
                tableColumn.getName()) + whereClause(id);
    }

    public String findAll() {
        return String.format(SELECT_QUERY_FORMAT, idColumn.getName(), columns.getColumnNames(),
                tableColumn.getName());
    }

    private String whereClause(Long id) {
        return String.format(WHERE_CLAUSE_FORMAT, idColumn.getName(), id);
    }

}
