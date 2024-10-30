package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.dialect.H2Dialect;
import persistence.metadata.WhereCondition;
import persistence.sql.dml.builder.DeleteQueryBuilder;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.builder.SelectQueryBuilder;
import persistence.sql.dml.query.DeleteQuery;
import persistence.sql.dml.query.InsertQuery;
import persistence.sql.dml.query.SelectColumnName;

import java.sql.Connection;
import java.util.List;

public class EntityManager {
    private final JdbcTemplate jdbcTemplate;
    private final SelectQueryBuilder selectQueryBuilder;
    private final InsertQueryBuilder insertQueryBuilder;

    public EntityManager(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
        this.selectQueryBuilder = new SelectQueryBuilder(new H2Dialect());
        this.insertQueryBuilder = new InsertQueryBuilder(new H2Dialect());
    }

    public <T> T find(Class<T> clazz, Long id) {
        SelectColumnName extractor = new SelectColumnName(clazz);
        String sql = this.selectQueryBuilder
                .select(extractor.columnNames())
                .from(extractor.tableName())
                .where(List.of(new WhereCondition("id", "=", id)))
                .build();
        return jdbcTemplate.queryForObject(sql, new EntityRowMapper<>(clazz));
    }


    public void persist(Object object) {
        InsertQuery insertQuery = new InsertQuery(object);
        String sql = this.insertQueryBuilder
                .insert(insertQuery.tableName(), insertQuery.columns())
                .values(insertQuery.columns())
                .build();

        jdbcTemplate.execute(sql);
    }

    public void remove(Object object) {
        DeleteQuery query = new DeleteQuery(object.getClass());
        String queryString = DeleteQueryBuilder.builder(new H2Dialect())
                .delete(query.tableName())
                .build();
    }
}
