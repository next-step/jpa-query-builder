package persistence.sql.dml;

public class ColumnClause {
    String columnName;

    public ColumnClause(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
