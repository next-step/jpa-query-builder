package persistence.sql.ddl.dialect.h2;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.entity.EntityData;

/**
 * H2 DB 쿼리 생성
 */
public class H2Dialect implements Dialect {

    private final H2CreateQuery createQuery = new H2CreateQuery();
    private final H2DropQuery dropQuery = new H2DropQuery();
    private final H2InsertQuery insertQuery = new H2InsertQuery();


    @Override
    public String getCreateQuery(EntityData entityData) {
        return createQuery.generateQuery(entityData);
    }

    @Override
    public String getDropQuery(EntityData entityData) {
        return dropQuery.generateQuery(entityData);
    }

    @Override
    public String getInsertQuery(EntityData entityData, Object entity) {
        return insertQuery.generateQuery(entityData, entity);
    }

}
