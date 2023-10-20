package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * CREATE 쿼리 생성
 */
public class DropQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Dialect dialect;

    public DropQueryBuilder(Dialect dialect, Class<?> objectClass) {
        validateObject(objectClass);
        this.entityData = new EntityData(objectClass);
        this.dialect = dialect;
    }

    /**
     * validate object class
     */
    private void validateObject(Class<?> objectClass) {
        if (!objectClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity 애노테이션이 붙은 클래스에 대해 테이블을 삭제할 수 있습니다.");
        }
    }

    @Override
    public String getQuery() {
        return dialect.getDropQuery(entityData);
    }
}
