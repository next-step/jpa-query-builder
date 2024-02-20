package persistence.inspector;

public class EntityColumn {
    private final String fieldName;
    private final String columnName;
    private final EntityColumnType type;
    private final boolean isPrimaryKey;
    private final boolean isNullable;
    private final boolean isAutoIncrement;

    public EntityColumn(String fieldName, String columnName, EntityColumnType type, boolean isPrimaryKey, boolean isNullable, boolean isAutoIncrement) {
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
        this.isNullable = isNullable;
        this.isAutoIncrement = isAutoIncrement;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public EntityColumnType getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public boolean isInsertTargetColumn() {
        return !isAutoIncrement;
    }
}
