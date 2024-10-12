package persistence.sql.ddl;

public record EntityInfo(String tableName, FieldInfo[] fields) {

    public PrimaryKey primaryKey() {
        for (FieldInfo field : fields) {
            if (field.isPrimaryKey()) {
                return field.primaryKey();
            }
        }

        throw new IllegalArgumentException("Primary key not found");
    }
}
