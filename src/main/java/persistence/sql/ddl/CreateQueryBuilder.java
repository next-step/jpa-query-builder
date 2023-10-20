package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * CREATE 쿼리 생성
 */
public class CreateQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Dialect dialect;

    public CreateQueryBuilder(Dialect dialect, Class<?> objectClass) {
        validateObject(objectClass);
        this.entityData = new EntityData(objectClass);
        this.dialect = dialect;
    }

    /**
     * validate object class
     */
    private void validateObject(Class<?> objectClass) {
        if (!objectClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity 애노테이션이 붙은 클래스에 대해 테이블을 생성할 수 있습니다.");
        }
    }

    @Override
    public String getQuery() {
        return dialect.getCreateQuery(entityData);
    }

}
