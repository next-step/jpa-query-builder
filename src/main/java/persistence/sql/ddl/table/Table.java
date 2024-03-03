package persistence.sql.ddl.table;

import persistence.sql.ddl.column.Columns;

public class Table {

    private final TableName name;
    private final Columns columns;

    private Table(TableName name, Columns columns) {
        this.name = name;
        this.columns = columns;
    }

    public static Table from(Class<?> entity) {
        TableName name = TableName.from(entity);
        Columns columns = Columns.from(entity);

        return new Table(name, columns);
    }

    public String getName() {
        return name.getName();
    }

    public String getColumnsDefinition() {
        return columns.getColumnsDefinition();
    }

    public String getColumnsClause() {
        return columns.getColumnsClause();
    }
}
