package persistence.sql.ddl.mapping;

import persistence.sql.ddl.model.Column;
import persistence.sql.Table;

public class DDLQueryBuilder implements QueryBuilder {

    private final Table table;
    private final Column column;

    public DDLQueryBuilder(Table table, Column column) {
        this.table = table;
        this.column = column;
    }

    @Override
    public String create(Class<?> clz) {
        return String.format(
                "CREATE TABLE %s (%s);",
                table.name(clz),
                column.create(clz)
        );
    }

    @Override
    public String drop(Class<?> clz) {
        return String.format(
                "DROP TABLE %s;",
                table.name(clz)
        );
    }

}
