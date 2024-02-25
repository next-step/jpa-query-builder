package persistence.sql.dml.builder;

import persistence.sql.Table;
import persistence.sql.dml.model.DMLColumn;

public class FindAllQueryBuilder {

    private final Table table;
    private final DMLColumn column;

    public FindAllQueryBuilder(Table table, DMLColumn column) {
        this.table = table;
        this.column = column;
    }

    public String query(Class<?> clz) {
        return String.format(
                "SELECT (%s) FROM %s;",
                column.fields(clz),
                table.name(clz)
        );
    }
}
