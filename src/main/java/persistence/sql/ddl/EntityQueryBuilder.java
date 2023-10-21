package persistence.sql.ddl;

public class EntityQueryBuilder {

    private final EntityMetadataExtractor entityMetadataExtractor;

    public EntityQueryBuilder(Class<?> type) {
        this.entityMetadataExtractor = new EntityMetadataExtractor(type);
    }

    public String create() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(entityMetadataExtractor.getTableName());
        sb.append(" (");
        sb.append(entityMetadataExtractor.getColumnInfo());
        sb.append(");");

        return sb.toString();
    }

    public String drop() {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(entityMetadataExtractor.getTableName());
        sb.append(";");

        return sb.toString();
    }

}
