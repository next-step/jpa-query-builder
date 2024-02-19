package persistence.inspector;

public class EntityColumn {

    private final String name;
    private final EntityColumnType type;
    private final boolean isPrimaryKey;
    private final boolean isNullable;
    private final boolean isAutoIncrement;

    public EntityColumn(String name, EntityColumnType type, boolean isPrimaryKey, boolean isNullable, boolean isAutoIncrement) {
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
        this.isNullable = isNullable;
        this.isAutoIncrement = isAutoIncrement;
    }

    public String getName() {
        return name;
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
}
