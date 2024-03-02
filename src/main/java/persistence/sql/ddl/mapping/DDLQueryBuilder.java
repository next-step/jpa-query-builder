package persistence.sql.ddl.mapping;

import persistence.sql.ddl.model.DDLColumn;
import persistence.sql.model.Table;

public class DDLQueryBuilder implements QueryBuilder {

    private static final String CREATE_QUERY_FORMAT = "CREATE TABLE %s (%s);";
    private static final String DROP_QUERY_FORMAT = "DROP TABLE %s;";

    private final Table table;
    private final DDLColumn column;

    public DDLQueryBuilder(Table table, DDLColumn column) {
        this.table = table;
        this.column = column;
    }

    @Override
    public String create(Class<?> clz) {
        return String.format(
                CREATE_QUERY_FORMAT,
                table.name(clz),
                column.create(clz)
        );
    }

    @Override
    public String drop(Class<?> clz) {
        return String.format(
                DROP_QUERY_FORMAT,
                table.name(clz)
        );
    }

}
