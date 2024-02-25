package persistence.sql.ddl.dto.db;

public class TableName {

    private final String name;

    public TableName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
