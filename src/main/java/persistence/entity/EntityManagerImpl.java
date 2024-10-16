package persistence.entity;

import database.DatabaseServer;
import jdbc.JdbcTemplate;
import persistence.sql.dml.query.DeleteByIdQueryBuilder;
import persistence.sql.dml.query.InsertQueryBuilder;
import persistence.sql.dml.query.SelectByIdQueryBuilder;

import java.sql.SQLException;

public class EntityManagerImpl implements EntityManager {

    private static final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
    private static final SelectByIdQueryBuilder selectByIdQueryBuilder = new SelectByIdQueryBuilder();
    private static final DeleteByIdQueryBuilder deleteByIdQueryBuilder = new DeleteByIdQueryBuilder();

    private final DatabaseServer databaseServer;

    public EntityManagerImpl(DatabaseServer databaseServer) {
        this.databaseServer = databaseServer;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        final String query = selectByIdQueryBuilder.build(clazz, Id);
        try {
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(databaseServer.getConnection());
            return jdbcTemplate.queryForObject(query, new GenericRowMapper<>(clazz));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
