package persistence.entity;

import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

public class EntityManagerImpl<T> implements EntityManager<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl() {
        this.jdbcTemplate = new JdbcTemplate(H2ConnectionFactory.newConnection());
    }

    @Override
    public T find(Class<T> entityClass, Long id) {
        final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(entityClass);
        final String sql = selectQueryBuilder.findById(id);
        return jdbcTemplate.queryForObject(sql, new CustomRowMapper<>(entityClass));
    }

    @Override
    public void persist(Object entity) {
        final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entity);
        final String sql = insertQueryBuilder.insert();
        jdbcTemplate.execute(sql);
    }
}
