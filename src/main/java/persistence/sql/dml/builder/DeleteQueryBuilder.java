package persistence.sql.dml.builder;

import persistence.sql.Table;
import persistence.sql.dml.model.DMLColumn;

public class DeleteQueryBuilder {

    private static final String DELETE_QUERY_FORMAT = "DELETE FROM %s WHERE %s = %s;";

    private final Table table;
    private final DMLColumn column;

    public DeleteQueryBuilder(Table table, DMLColumn column) {
        this.table = table;
        this.column = column;
    }

    public String query(Class<?> clz, Object id) {
        return String.format(
                DELETE_QUERY_FORMAT,
                table.name(clz),
                column.where(clz, id),
                id
        );
    }
}
