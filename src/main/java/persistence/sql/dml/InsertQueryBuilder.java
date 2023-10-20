package persistence.sql.dml;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * CREATE 쿼리 생성
 */
public class InsertQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Dialect dialect;
    private final Object entity;

    public InsertQueryBuilder(Dialect dialect, Object entity) {
        validateObject(entity.getClass());
        this.entityData = new EntityData(entity.getClass());
        this.dialect = dialect;
        this.entity = entity;
    }

    /**
     * validate object class
     */
    private void validateObject(Class<?> objectClass) {
        if (!objectClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity 애노테이션이 붙은 클래스에 대해서만 INSERT 쿼리를 수행할 수 있습니다.");
        }
    }

    @Override
    public String getQuery() {
        return dialect.getInsertQuery(entityData, entity);
    }

}
