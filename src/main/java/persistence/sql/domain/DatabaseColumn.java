package persistence.sql.domain;

public class DatabaseColumn {

    private static final String EQUAL = "=";

    protected final ColumnName name;

    protected final ColumnValue value;

    protected final ColumnLength size;

    protected final ColumnNullable nullable;

    public DatabaseColumn(ColumnName name, ColumnValue value, ColumnLength size, ColumnNullable nullable) {
        this.name = name;
        this.value = value;
        this.size = size;
        this.nullable = nullable;
    }

    public static DatabaseColumn copy(DatabaseColumn databaseColumn) {
        if (databaseColumn instanceof DatabasePrimaryColumn) {
            return DatabasePrimaryColumn.copy((DatabasePrimaryColumn) databaseColumn);
        }
        return new DatabaseColumn(databaseColumn.name, databaseColumn.value, databaseColumn.size, databaseColumn.nullable);
    }

    public String getName() {
        return name.getName();
    }

    public Class<?> getJavaType() {
        return value.getJavaType();
    }

    public Integer getSize() {
        return size.getSize();
    }

    public boolean isNullable() {
        return this.nullable.isNullable();
    }

    public String getValue() {
        return value.getValue();
    }

    public String whereClause() {
        return this.getName() + EQUAL + this.getValue();
    }
}
