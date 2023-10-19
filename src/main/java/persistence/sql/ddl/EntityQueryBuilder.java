package persistence.sql.ddl;

public class EntityQueryBuilder {

    private final EntityMetadata entityMetadata;

    public EntityQueryBuilder(Class<?> clazz) {
        this.entityMetadata = new EntityMetadata(clazz);
    }

    public String create() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(entityMetadata.getTableName());
        sb.append(" (");
        sb.append(entityMetadata.getColumnInfo());
        sb.append(");");

        return sb.toString();
    }

    public String drop() {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(entityMetadata.getTableName());
        sb.append(";");

        return sb.toString();
    }

}
