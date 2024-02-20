package database.sql.dml;

import java.util.Map;

public class QueryBuilder {
    public String buildInsertQuery(Class<?> entityClass, Map<String, Object> valueMap) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entityClass, valueMap);
        return insertQueryBuilder.buildQuery();
    }

    public String buildSelectQuery(Class<?> entityClass) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(entityClass);
        return selectQueryBuilder.buildQuery();
    }

    public String buildSelectOneQuery(Class<?> entityClass, Long id) {
        SelectOneQueryBuilder selectOneQueryBuilder = new SelectOneQueryBuilder(entityClass, id);
        return selectOneQueryBuilder.buildQuery();
    }

    public String buildDeleteQuery(Class<?> entityClass, Map<String, Object> conditionMap) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(entityClass, conditionMap);
        return deleteQueryBuilder.buildQuery();
    }
}
