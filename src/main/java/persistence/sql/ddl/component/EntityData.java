package persistence.sql.ddl.component;

public class EntityData {

    private final Class<?> entityClass;
    private final EntityName entityName = new EntityName();

    public EntityData(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        return entityName.getTableName(entityClass);
    }

}
