package persistence.entity;

import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.Connection;

public class EntityManagerImpl<T> implements EntityManager<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);

    private final Connection connection;

    public EntityManagerImpl() {
        connection = H2ConnectionFactory.newConnection();
    }

    @Override
    public T find(Class<T> entityClass, Long id) {
        final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(entityClass);
        final String sql = selectQueryBuilder.findById(id);
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);
        return jdbcTemplate.queryForObject(sql, new CustomRowMapper<>(entityClass));
    }

    @Override
    public void persist(Object entity) {
        final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entity);
        final String sql = insertQueryBuilder.insert();
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);
        jdbcTemplate.execute(sql);
    }
}
