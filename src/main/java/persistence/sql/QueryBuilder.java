package persistence.sql;

import persistence.sql.ddl.DdlQueryBuild;
import persistence.sql.dml.DmlQueryBuild;

public class QueryBuilder implements DmlQueryBuild, DdlQueryBuild {

    private final DmlQueryBuild dmlQueryBuilder;
    private final DdlQueryBuild ddlQueryBuilder;

    public QueryBuilder(DdlQueryBuild ddlQueryBuilder, DmlQueryBuild dmlQueryBuilder) {
        this.ddlQueryBuilder = ddlQueryBuilder;
        this.dmlQueryBuilder = dmlQueryBuilder;
    }

    @Override
    public String createQuery(Class<?> type) {
        return ddlQueryBuilder.createQuery(type);
    }

    @Override
    public String dropQuery(Class<?> type) {
        return ddlQueryBuilder.dropQuery(type);
    }

    @Override
    public <T> String insert(T entity) {
        return dmlQueryBuilder.insert(entity);
    }

    @Override
    public String findAll(Class<?> entity) {
        return dmlQueryBuilder.findAll(entity);
    }

    @Override
    public String findById(Class<?> entity, Object id) {
        return dmlQueryBuilder.findById(entity, id);
    }

    @Override
    public <T> String delete(T entity) {
        return dmlQueryBuilder.delete(entity);
    }
}
