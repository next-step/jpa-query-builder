package persistence.sql.ddl.mapping;

import persistence.sql.ddl.model.Column;
import persistence.sql.ddl.model.Table;

public class DDLQueryBuilder implements QueryBuilder {

    private final Table table;
    private final Column column;

    public DDLQueryBuilder(Table table, Column column) {
        this.table = table;
        this.column = column;
    }

    @Override
    public String create(Class<?> clz) {
        return new StringBuilder()
                .append(table.create(clz))
                .append(column.create(clz))
                .toString();
    }

    @Override
    public String drop(Class<?> clz) {
        return table.drop(clz);
    }

}
