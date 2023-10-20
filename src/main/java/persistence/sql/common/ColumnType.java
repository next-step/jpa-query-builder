package persistence.sql.common;

class ColumnType {
    private final String type;

    private <T> ColumnType(Class<T> tClass) {
        this.type = parse(tClass.getSimpleName());
    }

    protected static <T> ColumnType of(Class<T> tClass) {
        return new ColumnType(tClass);
    }

    private String parse(String type) {
        switch (type) {
            case "int":
                return "INT";
            case "Integer":
                return "INTEGER";
            case "Long":
                return "BIGINT";
            case "String":
                return "VARCHAR(255)";
            default:
                return null;
        }
    }

    public String getType() {
        return " " + type;
    }
}
