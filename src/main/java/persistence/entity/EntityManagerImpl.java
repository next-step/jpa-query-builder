package persistence.entity;

import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.Connection;

public class EntityManagerImpl<T> implements EntityManager<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);

    private final Connection connection;

    public EntityManagerImpl() {
        connection = H2ConnectionFactory.getConnection();
    }

    @Override
    public T find(Class<T> entityClass, Long id) {
        final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(entityClass);
        final String sql = selectQueryBuilder.findById(id);
        return queryForObject(sql, entityClass);
    }

    private  T queryForObject(String sql, Class<T> entityClass) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);
        return jdbcTemplate.queryForObject(sql, new CustomRowMapper<>(entityClass));
    }
}
