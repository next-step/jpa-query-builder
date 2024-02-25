package persistence.sql.dml;

import persistence.sql.column.IdColumn;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Dialect;

public class DeleteQueryBuilder {

    private static final String DELETE_QUERY_FORMAT = "delete from %s";
    private static final String WHERE_CLAUSE_FORMAT = " where %s = %d";

    private final Dialect dialect;
    private IdColumn idColumn;
    private String query;

    public DeleteQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public DeleteQueryBuilder build(Object entity) {
        Class<?> clazz = entity.getClass();
        TableColumn tableColumn = new TableColumn(clazz);
        this.idColumn = new IdColumn(clazz.getDeclaredFields(), dialect);
        this.query = String.format(DELETE_QUERY_FORMAT, tableColumn.getName());
        return this;
    }

    public String deleteById(Long id) {
        return query + String.format(WHERE_CLAUSE_FORMAT, idColumn.getName(), id);
    }
}
