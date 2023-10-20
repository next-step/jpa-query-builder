package persistence.sql.dialect.h2;

import persistence.sql.Query;
import persistence.sql.entity.EntityData;

/**
 * H2 DB 쿼리 생성
 */
public class H2Query implements Query {

    private final H2CreateQuery createQuery = new H2CreateQuery();
    private final H2DropQuery dropQuery = new H2DropQuery();
    private final H2InsertQuery insertQuery = new H2InsertQuery();
    private final H2FindAllQuery findAllQuery = new H2FindAllQuery();
    private final H2FindByIdQuery findByIdQuery = new H2FindByIdQuery();
    private final H2DeleteQuery deleteQuery = new H2DeleteQuery();

    @Override
    public String create(EntityData entityData) {
        return createQuery.generateQuery(entityData);
    }

    @Override
    public String drop(EntityData entityData) {
        return dropQuery.generateQuery(entityData);
    }

    @Override
    public String insert(EntityData entityData, Object entity) {
        return insertQuery.generateQuery(entityData, entity);
    }

    @Override
    public String findAll(EntityData entityData) {
        return findAllQuery.generateQuery(entityData);
    }

    @Override
    public String findById(EntityData entityData, Object id) {
        return findByIdQuery.generateQuery(entityData, id);
    }

    @Override
    public String delete(EntityData entityData, Object entity) {
        return deleteQuery.generateQuery(entityData, entity);
    }

}
