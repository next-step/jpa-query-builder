package persistence.sql.dml.builder;

import persistence.sql.dml.model.DMLColumn;
import persistence.sql.dml.model.Where;
import persistence.sql.model.Table;

public class DeleteQueryBuilder {

    private static final String DELETE_QUERY_FORMAT = "DELETE FROM %s WHERE %s;";

    private final Table table;
    private final DMLColumn column;
    private final Where where;

    public DeleteQueryBuilder(Table table, DMLColumn column, Where where) {
        this.table = table;
        this.column = column;
        this.where = where;
    }

    public String build() {
        return String.format(
                DELETE_QUERY_FORMAT,
                table.name(),
                where.findByEntity(column.getValue())
        );
    }
}
