package persistence.sql;

import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.entity.EntityData;

public class Query<T, K> {

    private final Dialect dialect;

    public Query(Dialect dialect) {
        this.dialect = dialect;
    }

    public String create(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new CreateQueryBuilder(dialect).generateQuery(entityData);
    }

    public String drop(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new DropQueryBuilder().generateQuery(entityData);
    }

    public String insert(T entity) {
        EntityData entityData = new EntityData(entity.getClass());
        return new InsertQueryBuilder(dialect).generateQuery(entityData, entity);
    }

    public String findAll(Class<T> entityClass) {
        EntityData entityData = new EntityData(entityClass);
        return new SelectQueryBuilder().generateQuery(entityData);
    }

    public String findById(Class<T> entityClass, K id) {
        EntityData entityData = new EntityData(entityClass);
        return new SelectQueryBuilder()
                .appendWhereClause(entityData.getEntityColumns().getIdColumn().getColumnName(), id)
                .generateQuery(entityData);
    }

    public String delete(T entity) {
        EntityData entityData = new EntityData(entity.getClass());
        return new DeleteQueryBuilder().generateQuery(entityData, entity);
    }

}
