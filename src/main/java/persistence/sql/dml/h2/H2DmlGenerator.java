package persistence.sql.dml.h2;

import persistence.core.EntityMetadataModelHolder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.DmlGenerator;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.dml.where.FetchWhereQuery;

public class H2DmlGenerator implements DmlGenerator {

    private final InsertQueryBuilder insertQueryBuilder;

    private final SelectQueryBuilder selectQueryBuilder;


    private final DeleteQueryBuilder deleteQueryBuilder;

    public H2DmlGenerator(EntityMetadataModelHolder entityMetadataModelHolder) {
        this.insertQueryBuilder = new H2InsertQueryBuilder(entityMetadataModelHolder);
        this.selectQueryBuilder = new H2SelectQueryBuilder(entityMetadataModelHolder);
        this.deleteQueryBuilder = new H2DeleteQueryBuilder(entityMetadataModelHolder);
    }

    @Override
    public String insert(Object entity) {
        return insertQueryBuilder.createInsertQuery(entity);
    }

    @Override
    public String findAll(Class<?> entity) {
        return selectQueryBuilder.findAll(entity);
    }

    @Override
    public String findBy(Class<?> entity, FetchWhereQuery whereClauses) {
        return selectQueryBuilder.findBy(entity, whereClauses);
    }

    @Override
    public String delete(Class<?> entity, FetchWhereQuery whereClauses) {
        return deleteQueryBuilder.delete(entity, whereClauses);
    }
}
