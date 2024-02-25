package persistence.sql.dml.builder;

import persistence.sql.Table;
import persistence.sql.dml.model.DMLColumn;

public class InsertQueryBuilder {

    private final Table table;
    private final DMLColumn column;

    public InsertQueryBuilder(Table table, DMLColumn column) {
        this.table = table;
        this.column = column;
    }

    public String query(Class<?> clz, Object entity) {
        return String.format(
                "INSERT INTO %s (%s) VALUES (%s);",
                table.name(clz),
                column.fields(clz),
                column.values(clz, entity));
    }
}
