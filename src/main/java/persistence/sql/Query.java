package persistence.sql;

import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.entity.EntityData;

public class Query {

    private final Dialect dialect;

    public Query(Dialect dialect) {
        this.dialect = dialect;
    }

    public <T> String create(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new CreateQueryBuilder(dialect).generateQuery(entityData);
    }

    public <T> String drop(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new DropQueryBuilder().generateQuery(entityData);
    }

    public <T> String insert(T entity) {
        EntityData entityData = new EntityData(entity.getClass());
        return new InsertQueryBuilder(dialect).generateQuery(entityData, entity);
    }

    public <T> String findAll(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new SelectQueryBuilder().generateQuery(entityData);
    }

    public <T, K> String findById(Class<T> entityClass, K id) {
        EntityData entityData = new EntityData(entityClass);
        return new SelectQueryBuilder()
                .appendWhereClause(entityData.getEntityColumns().getIdColumn().getColumnName(), id)
                .generateQuery(entityData);
    }

    public <T> String delete(T entity) {
        EntityData entityData = new EntityData(entity.getClass());
        return new DeleteQueryBuilder().generateQuery(entityData, entity);
    }

}
