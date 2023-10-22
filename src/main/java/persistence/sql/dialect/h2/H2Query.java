package persistence.sql.dialect.h2;

import persistence.sql.Query;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.entity.EntityData;

public class H2Query<T, K> implements Query<T, K> {

    private static final H2Dialect dialect = new H2Dialect();

    @Override
    public String create(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new CreateQueryBuilder(dialect).generateQuery(entityData);
    }

    @Override
    public String drop(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new DropQueryBuilder().generateQuery(entityData);
    }

    @Override
    public String insert(T entity) {
        EntityData entityData = new EntityData(entity.getClass());
        return new InsertQueryBuilder(dialect).generateQuery(entityData, entity);
    }

    @Override
    public String findAll(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new SelectQueryBuilder().generateQuery(entityData);
    }

    @Override
    public String findById(Class<T> entityClass, K id) {
        EntityData entityData = new EntityData(entityClass);
        return new SelectQueryBuilder()
                .appendWhereClause(entityData.getEntityColumns().getIdColumn().getColumnName(), id)
                .generateQuery(entityData);
    }

    @Override
    public String delete(T entity) {
        EntityData entityData = new EntityData(entity.getClass());
        return new DeleteQueryBuilder().generateQuery(entityData, entity);
    }

}
