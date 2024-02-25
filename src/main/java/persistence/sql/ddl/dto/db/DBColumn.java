package persistence.sql.ddl.dto.db;

public class DBColumn {

    private final String name;
    private final String type;
    private final boolean primaryKey;

    public DBColumn(String name, String type, boolean primaryKey) {
        validate(name, type);

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

    private void validate(String name, String type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("DB 컬럼 이름은 비어있을 수 없습니다.");
        }

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("DB 컬럼 타입은 비어있을 수 없습니다.");
        }
    }
}
