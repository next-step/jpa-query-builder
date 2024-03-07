package persistence.sql.dto.database;

public class Column {

    private final String name;
    private final String type;
    private final boolean primaryKey;
    private final boolean autoIncrement;
    private final boolean nullable;

    public Column(String name, String type, boolean primaryKey, boolean autoIncrement, boolean nullable) {
        validate(name, type);

        this.name = name;
        this.type = type;
        this.primaryKey = primaryKey;
        this.autoIncrement = autoIncrement;
        this.nullable = nullable;
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

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isVarcharType() {
        return "VARCHAR".equals(type);
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
