package persistence.sql.ddl.dto.db;

public class DBColumn {

    private final String name;
    private final String type;
    private final boolean primaryKey;

    public DBColumn(String name, String type, boolean primaryKey) {
        this.name = name;
        this.type = type;
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }
}
