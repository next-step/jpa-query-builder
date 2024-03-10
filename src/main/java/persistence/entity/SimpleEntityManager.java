package persistence.entity;

import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.dml.WhereRecord;

import java.util.List;

public class SimpleEntityManager implements EntityManager {

    private final JdbcTemplate jdbcTemplate;
    private final Dialect dialect;

    public SimpleEntityManager(JdbcTemplate jdbcTemplate, Dialect dialect) {
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.builder()
                .dialect(dialect)
                .entity(clazz)
                .where(List.of(new WhereRecord("id", "=", Id)))
                .build();

        return jdbcTemplate.queryForObject(selectQueryBuilder.generateQuery(), resultSet -> new EntityRowMapper<T>(clazz).mapRow(resultSet));
    }

    @Override
    public Object persist(Object entity) {
        InsertQueryBuilder insertQueryBuilder = InsertQueryBuilder.builder()
                .dialect(dialect)
                .entity(entity)
                .build();

        jdbcTemplate.execute(insertQueryBuilder.generateQuery());

        return entity;
    }

    @Override
    public void remove(Object entity) {

    }
}
