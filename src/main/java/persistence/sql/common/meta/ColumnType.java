package persistence.sql.common.meta;

enum ColumnType {
    INT(null),
    INTEGER(null),
    BIGINT(null),
    VARCHAR(255),
    NULL(null)
    ;

    private Integer defaultSize;

    ColumnType(Integer defaultSize) {
        this.defaultSize = defaultSize;
    }

    public static <T> ColumnType of(Class<T> tClass) {
        return switch (tClass.getSimpleName()) {
            case "int" -> INT;
            case "Integer" -> INTEGER;
            case "Long" -> BIGINT;
            case "String" -> VARCHAR;
            default -> NULL;
        };
    }

    protected String getType() {
        String type = " " + this;
        if (defaultSize != null) {
            type = type + String.format("(%s)", this.defaultSize);
        }
        return type;
    }
}
