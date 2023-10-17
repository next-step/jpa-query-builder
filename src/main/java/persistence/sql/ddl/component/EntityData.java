package persistence.sql.ddl.component;

public class EntityData {

    private final Class<?> entityClass;
    private final EntityColumns entityColumns;

    public EntityData(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.entityColumns = new EntityColumns(entityClass);
    }

    public String getTableName() {
        return EntityName.getTableName(entityClass);
    }

    public String getCreateQueryBody() {
        return null;
    }

}
