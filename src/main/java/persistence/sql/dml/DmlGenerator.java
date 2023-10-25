package persistence.sql.dml;

import persistence.core.EntityMetadataModelHolder;
import persistence.sql.dml.where.EntityCertification;
import persistence.sql.dml.where.FetchWhereQuery;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.dml.where.WhereQueryBuilder;

import java.util.List;

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

    public String findById(Class<?> entity, Long id) {
        EntityCertification<?> certification = EntityCertification.certification(entity);
        WhereQuery idEqual = certification.equal("id", id);;
        return selectQueryBuilder.findBy(entity, WhereQueryBuilder.builder().and(List.of(idEqual)));
    }

    public String findBy(Class<?> entity, FetchWhereQuery whereClauses) {
        return selectQueryBuilder.findBy(entity, whereClauses);
    }

    public String delete(Class<?> entity, FetchWhereQuery whereClauses) {
        return deleteQueryBuilder.delete(entity, whereClauses);
    }
}
