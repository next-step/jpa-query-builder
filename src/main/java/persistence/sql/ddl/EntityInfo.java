package persistence.sql.ddl;

public record EntityInfo(String tableName, FieldInfo[] fields) {

    public FieldInfo primaryKey() {
        for (FieldInfo field : fields) {
            if (field.isPrimaryKey()) {
                return field;
            }
        }

        throw new IllegalArgumentException("Primary key not found");
    }
}
