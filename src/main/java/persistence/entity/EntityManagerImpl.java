package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.SQLException;

public class EntityManagerImpl<T> implements EntityManager<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);
    private final DatabaseServer server;

    public EntityManagerImpl() {
        try {
            this.server = new H2();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T find(Class<T> entityClass, Long id) {
        final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(entityClass);
        final String sql = selectQueryBuilder.findById(id);
        return queryForObject(sql, entityClass);
    }

    private  T queryForObject(String sql, Class<T> entityClass) {
        try {
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            return jdbcTemplate.queryForObject(sql, new CustomRowMapper<>(entityClass));
        } catch (SQLException e) {
            throw new IllegalStateException("서버 연결에 실패하였습니다.");
        }
    }
}
