package persistence.sql.ddl;

import persistence.sql.column.TableColumn;

public class DropQueryBuilder implements QueryBuilder {

    private static final String DROP_TABLE = "drop table ";

    private final TableColumn tableColumn;

    private DropQueryBuilder(TableColumn tableColumn) {
        this.tableColumn = tableColumn;
    }

    public static DropQueryBuilder generate(Class<?> clazz) {
        TableColumn tableColumn = TableColumn.from(clazz);
        return new DropQueryBuilder(tableColumn);
    }

    @Override
    public String build() {
        return DROP_TABLE + tableColumn.getName();
    }
}
