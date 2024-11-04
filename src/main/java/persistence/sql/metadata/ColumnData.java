package persistence.sql.metadata;

public class ColumnData {
    private final Column column;
    private final ColumnValue value;

    public ColumnData(Column column, ColumnValue value) {
        this.column = column;
        this.value = value;
    }

    public boolean isPrimaryKey() {
        return column.primaryKey();
    }

    public String getName() {
        return column.name().value();
    }

    public String getValue() {
        return value.toString();
    }

    public boolean hasNotIdentityStrategy() {
        return column.hasNotIdentityStrategy();
    }
}
