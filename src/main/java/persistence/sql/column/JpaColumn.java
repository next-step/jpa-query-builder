package persistence.sql.column;

public class JpaColumn implements Column {

    private final String name;
    private final ColumnType columnType;
    private final String nullable;

    public JpaColumn(String name, ColumnType columnType, String nullable) {
        this.name = name;
        this.columnType = columnType;
        this.nullable = nullable;
    }

    @Override
    public String getDefinition() {
        return this.name + columnType.getColumnDefinition() + nullable;
    }
}
