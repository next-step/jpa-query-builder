package persistence.sql.entity;

/**
 * 엔티티 정보 모음
 */
public class EntityData {

    private final Class<?> entityClass;
    private final EntityName entityName;
    private final EntityColumns entityColumns;

    public EntityData(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.entityName = new EntityName(entityClass);
        this.entityColumns = new EntityColumns(entityClass);
    }

    /**
     * 테이블 이름 가져오기
     */
    public String getTableName() {
        return entityName.getTableName();
    }

    /**
     * ID 컬럼 가져오기
     */
    public EntityColumn getPrimaryKey() {
        return entityColumns.getIdColumn();
    }

    /**
     * 엔티티 컬럼들 가져오기
     */
    public EntityColumns getEntityColumns() {
        return entityColumns;
    }

}
