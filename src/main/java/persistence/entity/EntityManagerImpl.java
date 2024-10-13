package persistence.entity;

import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityManagerImpl<T> implements EntityManager<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);
    private static final String SERVER_CREATION_FAILED_MESSAGE = "서버 생성에 실패하였습니다.";
    private static final String CONNECTION_FAILED_MESSAGE = "서버 연결에 실패하였습니다.";

    private final Connection connection;

    public EntityManagerImpl() {
        final H2 server;
        try {
            server = new H2();
        } catch (SQLException e) {
            throw new IllegalStateException(SERVER_CREATION_FAILED_MESSAGE, e);
        }
        
        try {
            this.connection = server.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(CONNECTION_FAILED_MESSAGE, e);
        }
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
