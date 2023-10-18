package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.entity.EntityColumn;
import persistence.sql.ddl.entity.EntityData;

import java.util.List;

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
        StringBuilder query = new StringBuilder();
        // "create table "
        query.append(dialect.CREATE_QUERY);
        query.append(dialect.SPACE);

        // 테이블 명
        appendTableName(query);

        query.append(dialect.OPEN_PARENTHESIS);
        // 컬럼 명
        appendColumns(query);
        // 기본키
        appendPrimaryKey(query);
        query.append(dialect.CLOSE_PARENTHESIS);

        return query.toString();
    }

    private void appendTableName(StringBuilder query) {
        query.append(entityData.getTableName());
        query.append(dialect.SPACE);
    }

    private void appendColumns(StringBuilder query) {
        List<EntityColumn> entityColumns = entityData.getEntityColumns().getEntityColumns();
        for (EntityColumn entityColumn : entityColumns) {
            query.append(dialect.getColumnPartInCreateQuery(entityColumn));
        }
    }

    private void appendPrimaryKey(StringBuilder query) {
        query.append(dialect.getPrimaryKeyInCreateQuery(entityData.getPrimaryKey()));
    }

}
