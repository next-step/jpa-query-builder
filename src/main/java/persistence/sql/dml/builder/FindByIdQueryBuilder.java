package persistence.sql.dml.builder;

import persistence.sql.Table;
import persistence.sql.dml.model.DMLColumn;

public class FindByIdQueryBuilder {

    private final Table table;
    private final DMLColumn column;

    public FindByIdQueryBuilder(Table table, DMLColumn column) {
        this.table = table;
        this.column = column;
    }

    public String query(Class<?> clz, Object id) {
        return String.format(
                "SELECT (%s) FROM %s WHERE %s = %s;",
                column.fields(clz),
                table.name(clz),
                column.where(clz, id), id
        );
    }
}
