package persistence.sql.ddl;

import persistence.sql.column.*;

public class CreateQueryBuilder implements DdlQueryBuilder {

    private static final String CREATE_TABLE_DDL = "create table %s (%s, %s)";

    private final TableColumn tableColumn;
    private final Columns columns;
    private final IdColumn idColumn;

    public CreateQueryBuilder(TableColumn tableColumn, Columns columns, IdColumn idColumn) {
        this.tableColumn = tableColumn;
        this.columns = columns;
        this.idColumn = idColumn;
    }

    @Override
    public String build() {
        return String.format(CREATE_TABLE_DDL, tableColumn.getName(), idColumn.getDefinition(), columns.getColumnsDefinition());
    }
}
