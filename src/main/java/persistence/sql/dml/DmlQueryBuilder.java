package persistence.sql.dml;

import persistence.sql.domain.Query;

public class DmlQueryBuilder implements InsertQueryBuild, SelectQueryBuild, DeleteQueryBuild {

    private final InsertQueryBuild insertQueryBuilder;

    private final SelectQueryBuild selectQueryBuilder;

    private final DeleteQueryBuild deleteQueryBuilder;

    public DmlQueryBuilder() {
        this.insertQueryBuilder = new InsertQueryBuilder();
        this.selectQueryBuilder = new SelectQueryBuilder();
        this.deleteQueryBuilder = new DeleteQueryBuilder();
    }


    @Override
    public <T> Query insert(T entity) {
        return insertQueryBuilder.insert(entity);
    }

    @Override
    public Query findAll(Class<?> entity) {
        return selectQueryBuilder.findAll(entity);
    }

    @Override
    public Query findById(Class<?> entity, Object id) {
        return selectQueryBuilder.findById(entity, id);

    }

    @Override
    public <T> Query delete(T entity) {
        return deleteQueryBuilder.delete(entity);
    }
}
