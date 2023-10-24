package persistence.sql.dml;

import persistence.core.EntityMetadataModelHolder;
import persistence.sql.dml.where.FetchWhereQuery;

public class DmlGenerator {

    private final InsertQueryBuilder insertQueryBuilder;

    private final SelectQueryBuilder selectQueryBuilder;


    private final DeleteQueryBuilder deleteQueryBuilder;

    public DmlGenerator(EntityMetadataModelHolder entityMetadataModelHolder) {
        this.insertQueryBuilder = new InsertQueryBuilder(entityMetadataModelHolder);
        this.selectQueryBuilder = new SelectQueryBuilder(entityMetadataModelHolder);
        this.deleteQueryBuilder = new DeleteQueryBuilder(entityMetadataModelHolder);
    }

    public String insert(Object entity) {
        return insertQueryBuilder.createInsertQuery(entity);
    }

    public String findAll(Class<?> entity) {
        return selectQueryBuilder.findAll(entity);
    }

    public String findBy(Class<?> entity, FetchWhereQuery whereClauses) {
        return selectQueryBuilder.findBy(entity, whereClauses);
    }

    public String delete(Class<?> entity, FetchWhereQuery whereClauses) {
        return deleteQueryBuilder.delete(entity, whereClauses);
    }
}
