package persistence.fixtures;

import jdbc.JdbcTemplate;
import persistence.dialect.H2Dialect;
import persistence.sql.dml.builder.CreateQueryBuilder;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.query.CreateQuery;
import persistence.sql.dml.query.InsertQuery;

public class QueryExecutor {
    public static void insert(Object object, JdbcTemplate jdbcTemplate) {
        InsertQuery insertQuery = new InsertQuery(object);
        InsertQueryBuilder insertQueryBuilder = InsertQueryBuilder.builder(new H2Dialect())
                .insert(insertQuery.tableName(), insertQuery.columns())
                .values(insertQuery.columns());
        jdbcTemplate.execute(insertQueryBuilder.build());
    }

    public static void create(Class<?> clazz, JdbcTemplate jdbcTemplate) {
        CreateQuery query = new CreateQuery(clazz);
        CreateQueryBuilder queryBuilder = CreateQueryBuilder.builder(new H2Dialect())
                .create(query.tableName(), query.identifier(), query.columns());

        jdbcTemplate.execute(queryBuilder.build());
    }
}
