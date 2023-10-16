package persistence.sql.ddl;

public class ColumnBuilder {
    private String name;

    private String type;

    private boolean isPrimaryKey;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public ColumnBuilder(String name, Class<?> type, boolean isPrimaryKey) {
        this.name = name;
        this.type = convertTypeToString(type);
        this.isPrimaryKey = isPrimaryKey;
    }

    private String convertTypeToString(Class<?> type) {
        switch (type.getSimpleName()) {
            case "Long" :
                return "BIGINT";

            case "String" :
                return "VARCHAR(255)";

            case "Integer" :
                return "INTEGER";

            default:
                throw new IllegalArgumentException(type.getSimpleName() + " : 정의되지 않은 타입입니다.");
        }
    }

}
