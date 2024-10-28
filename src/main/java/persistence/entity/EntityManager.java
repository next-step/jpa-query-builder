package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.dialect.H2Dialect;
import persistence.metadata.WhereCondition;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.builder.SelectQueryBuilder;
import persistence.sql.dml.query.InsertQuery;
import persistence.sql.dml.query.SelectQuery;

import java.sql.Connection;
import java.util.List;

public class EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManager(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    public <T> T find(Class<T> clazz, Long id) {
        SelectQuery extractor = new SelectQuery(clazz);
        String sql = SelectQueryBuilder.builder(new H2Dialect())
                .select(extractor.columnNames())
                .from(extractor.tableName())
                .where(List.of(new WhereCondition("id", "=", id)))
                .build();
        return jdbcTemplate.queryForObject(sql, new EntityRowMapper<>(clazz));
    }


    public void persist(Object object) {
        InsertQuery insertQuery = new InsertQuery(object);
        String sql = InsertQueryBuilder.builder(new H2Dialect())
                .insert(insertQuery.tableName(), insertQuery.columns())
                .values(insertQuery.columns())
                .build();

        jdbcTemplate.execute(sql);
    }

    public void remove(Object object) {

    }
}
