package persistence.sql.ddl;


public class DropTableQuery {

    private static final String TABLE_DROP_PREFIX = "DROP TABLE IF EXISTS";
    private final EntityTableMetadata entityTableMetadata;

    public DropTableQuery(Class<?> entityClass) {
        new ValidateEntity(entityClass);
        this.entityTableMetadata = new EntityTableMetadata(entityClass);
    }

    public String generateQuery() {
        String tableName = entityTableMetadata.getTableName();
        return  generateSql(tableName);
    }

    private String generateSql(String tableName) {
        return TABLE_DROP_PREFIX + " " + tableName + ";";
    }

}
