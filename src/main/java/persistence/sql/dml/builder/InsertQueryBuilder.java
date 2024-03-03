package persistence.sql.dml.builder;

import persistence.sql.model.Table;
import persistence.sql.dml.model.DMLColumn;

public class InsertQueryBuilder {

    private static final String INSERT_QUERY_FORMAT = "INSERT INTO %s (%s) VALUES (%s);";

    private final Table table;
    private final DMLColumn column;

    public InsertQueryBuilder(Table table, DMLColumn column) {
        this.table = table;
        this.column = column;
    }

    public String build(Object entity) {
        return String.format(
                INSERT_QUERY_FORMAT,
                table.name(),
                column.fields(),
                column.values()
        );
    }
}
