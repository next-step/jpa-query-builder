package persistence.query;

import jdbc.JdbcTemplate;
import persistence.sql.ddl.query.CreateQuery;
import persistence.sql.ddl.query.DropQuery;
import persistence.sql.ddl.query.builder.CreateQueryBuilder;
import persistence.sql.ddl.query.builder.DropQueryBuilder;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.query.InsertQuery;
import persistence.sql.dml.query.builder.InsertQueryBuilder;

public class QueryExecutor {

    public static void create(Class<?> clazz, JdbcTemplate jdbcTemplate) {
        CreateQuery query = new CreateQuery(clazz);
        CreateQueryBuilder queryBuilder = CreateQueryBuilder.builder(new H2Dialect())
                .create(query.tableName(), query.identifier(), query.columns());

        jdbcTemplate.execute(queryBuilder.build());
    }

    public static void drop(Class<?> clazz, JdbcTemplate jdbcTemplate) {
        DropQuery dropQuery = new DropQuery(clazz);
        DropQueryBuilder dropQueryBuilder = DropQueryBuilder.builder(new H2Dialect())
                .drop(dropQuery.tableName());

        jdbcTemplate.execute(dropQueryBuilder.build());
    }

    public static void insert(Object object, JdbcTemplate jdbcTemplate) {
        InsertQuery insertQuery = new InsertQuery(object);
        InsertQueryBuilder insertQueryBuilder = InsertQueryBuilder.builder(new H2Dialect())
                .insert(insertQuery.tableName(), insertQuery.columns())
                .values(insertQuery.columns());
        jdbcTemplate.execute(insertQueryBuilder.build());
    }


}
