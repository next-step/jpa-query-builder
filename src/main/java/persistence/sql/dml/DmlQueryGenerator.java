package persistence.sql.dml;

import persistence.sql.dialect.Dialect;
import persistence.sql.dml.builder.DeleteQueryBuilder;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.builder.SelectQueryBuilder;
import persistence.sql.meta.EntityMeta;

public class DmlQueryGenerator {

    private final Dialect dialect;

    private DmlQueryGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public static DmlQueryGenerator of(Dialect dialect) {
        return new DmlQueryGenerator(dialect);
    }

    public String generateInsertQuery(Object entity) {
        return InsertQueryBuilder.of(entity).build();
    }

    public String generateSelectAllQuery(Class<?> clazz) {
        EntityMeta entityMeta = EntityMeta.of(clazz);
        return SelectQueryBuilder.of(entityMeta).buildSelectAllQuery();
    }

    public String generateSelectByPkQuery(Class<?> clazz, Object pkObject) {
        EntityMeta entityMeta = EntityMeta.of(clazz);
        return SelectQueryBuilder.of(entityMeta).buildSelectByPkQuery(pkObject);
    }

    public String generateDeleteAllQuery(Class<?> clazz) {
        EntityMeta entityMeta = EntityMeta.of(clazz);
        return DeleteQueryBuilder.of(entityMeta).buildDeleteAllQuery();
    }

    public String generateDeleteQuery(Object entity) {
        EntityMeta entityMeta = EntityMeta.of(entity.getClass());
        return DeleteQueryBuilder.of(entityMeta).buildDeleteQuery(entity);
    }

    public String generateDeleteByPkQuery(Class<?> clazz, Object pkObject) {
        EntityMeta entityMeta = EntityMeta.of(clazz);
        return DeleteQueryBuilder.of(entityMeta).buildDeleteByPkQuery(pkObject);
    }

}
