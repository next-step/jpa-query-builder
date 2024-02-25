package database.sql.dml;

import java.util.Map;

public class QueryBuilder {
    private static final QueryBuilder INSTANCE = new QueryBuilder();

    private QueryBuilder() {
    }

    public static QueryBuilder getInstance() {
        return INSTANCE;
    }

    public String buildInsertQuery(Class<?> entityClass, Map<String, Object> valueMap) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entityClass);
        return insertQueryBuilder.buildQuery(valueMap);
    }

    public String buildInsertQuery(Object entity) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entity.getClass());
        return insertQueryBuilder.buildQuery(entity);
    }

    public String buildSelectQuery(Class<?> entityClass) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(entityClass);
        return selectQueryBuilder.buildQuery();
    }

    public String buildSelectOneQuery(Class<?> entityClass, Long id) {
        SelectOneQueryBuilder selectOneQueryBuilder = new SelectOneQueryBuilder(entityClass);
        return selectOneQueryBuilder.buildQuery(id);
    }

    public String buildDeleteQuery(Class<?> entityClass, Map<String, Object> conditionMap) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(entityClass);
        return deleteQueryBuilder.buildQuery(conditionMap);
    }

    public String buildDeleteQuery(Class<?> entityClass, Long id) {
        return buildDeleteQuery(entityClass, Map.of("id", id));
    }
}
