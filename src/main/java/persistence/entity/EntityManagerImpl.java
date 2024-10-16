package persistence.entity;

import database.DatabaseServer;
import jdbc.JdbcTemplate;
import persistence.sql.definition.TableDefinition;
import persistence.sql.definition.TableId;
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
        final String query = insertQueryBuilder.build(entity);
        try {
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(databaseServer.getConnection());
            jdbcTemplate.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public void remove(Object entity) {
        final TableDefinition tableDefinition = new TableDefinition(entity.getClass());
        final TableId tableId = tableDefinition.tableId();

        if (! tableId.hasValue(entity)) {
            throw new IllegalArgumentException("Entity does not have an id");
        }

        final Object id = new TableDefinition(entity.getClass()).tableId().getValue(entity);
        final String query = deleteByIdQueryBuilder.build(entity.getClass(), id);

        try {
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(databaseServer.getConnection());
            jdbcTemplate.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
