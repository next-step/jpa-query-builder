package persistence.sql.dml.builder;

import persistence.sql.model.Table;
import persistence.sql.dml.model.DMLColumn;

public class DeleteQueryBuilder {

    private static final String DELETE_QUERY_FORMAT = "DELETE FROM %s WHERE %s;";

    private final Table table;
    private final DMLColumn column;

    public DeleteQueryBuilder(Table table, DMLColumn column) {
        this.table = table;
        this.column = column;
    }

    public String query(Object entity) {
        return String.format(
                DELETE_QUERY_FORMAT,
                table.name(entity.getClass()),
                column.whereByEntity()
        );
    }
}
