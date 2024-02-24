package persistence.sql.ddl;

import persistence.sql.column.TableColumn;

public class DropQueryBuilder implements DdlQueryBuilder {

    private static final String DROP_TABLE = "drop table ";

    private final TableColumn tableColumn;

    public DropQueryBuilder(TableColumn tableColumn) {
        this.tableColumn = tableColumn;
    }

    @Override
    public String build() {
        return DROP_TABLE + tableColumn.getName();
    }
}
