package database.sql.dml;

import database.sql.IQueryBuilder;

import java.util.Map;
import java.util.regex.Pattern;

public class QueryBuilder {
    static Pattern numberPattern = Pattern.compile("^[0-9]+$");

    public String buildInsertQuery(Class<?> entityClass, Map<String, Object> valueMap) {
        IQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entityClass, valueMap);
        return insertQueryBuilder.buildQuery();
    }

    public String buildSelectQuery(Class<?> entityClass) {
        IQueryBuilder selectQueryBuilder = new SelectQueryBuilder(entityClass);
        return selectQueryBuilder.buildQuery();
    }
}
