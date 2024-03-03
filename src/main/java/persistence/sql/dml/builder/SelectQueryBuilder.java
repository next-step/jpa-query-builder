package persistence.sql.dml.builder;

import persistence.sql.model.Table;
import persistence.sql.dml.model.DMLColumn;

public class SelectQueryBuilder {

    private static final String FIND_ALL_QUERY_FORMAT = "SELECT %s FROM %s;";
    private static final String FIND_BY_ID_QUERY_FORMAT = "SELECT %s FROM %s WHERE %s = %s;";

    private final Table table;
    private final DMLColumn column;

    public SelectQueryBuilder(Table table, DMLColumn column) {
        this.table = table;
        this.column = column;
    }

    public String findAll(Class<?> clz) {
        return String.format(
                FIND_ALL_QUERY_FORMAT,
                column.fields(),
                table.name()
        );
    }

    public String build(Class<?> clz, Object id) {
        return String.format(
                FIND_BY_ID_QUERY_FORMAT,
                column.fields(),
                table.name(),
                column.whereById(id),
                id
        );
    }
}
