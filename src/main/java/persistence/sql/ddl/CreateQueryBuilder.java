package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * CREATE 쿼리 생성
 */
public class CreateQueryBuilder implements QueryBuilder {

    private final Query query;

    public CreateQueryBuilder(Query query) {
        this.query = query;
    }

    public String getQuery(Class<?> entityClass) {
        validateEntityClass(entityClass);
        return query.create(new EntityData(entityClass));
    }

    private void validateEntityClass(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity 애노테이션이 붙은 클래스에 대해서만 쿼리를 수행할 수 있습니다.");
        }
    }

}
