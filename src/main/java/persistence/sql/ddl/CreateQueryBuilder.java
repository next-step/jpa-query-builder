package persistence.sql.ddl;

import persistence.sql.column.*;
import persistence.sql.dialect.Database;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder implements DdlQueryBuilder {

    private static final String CREATE_TABLE_DDL = "create table %s (%s)";

    private final TableColumn tableColumn;

    public CreateQueryBuilder(TableColumn tableColumn) {
        this.tableColumn = tableColumn;
    }

    @Override
    public String build() {
        return String.format(CREATE_TABLE_DDL, tableColumn.getName(), tableColumn.getColumns().getColumnsDefinition());
    }
}
