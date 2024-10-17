package persistence.entity;

import database.DatabaseServer;
import jdbc.JdbcTemplate;
import persistence.sql.Queryable;
import persistence.sql.definition.TableDefinition;
import persistence.sql.definition.TableId;
import persistence.sql.dml.query.DeleteByIdQueryBuilder;
import persistence.sql.dml.query.InsertQueryBuilder;
import persistence.sql.dml.query.SelectByIdQueryBuilder;
import persistence.sql.dml.query.UpdateQueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class EntityManagerImpl implements EntityManager {

    private static final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
    private static final SelectByIdQueryBuilder selectByIdQueryBuilder = new SelectByIdQueryBuilder();
    private static final DeleteByIdQueryBuilder deleteByIdQueryBuilder = new DeleteByIdQueryBuilder();

    private final DatabaseServer databaseServer;

    public EntityManagerImpl(DatabaseServer databaseServer) {
        this.databaseServer = databaseServer;
    }

    @Override
    public <T> T find(Class<T> clazz, Object Id) {
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

        if (!tableId.hasValue(entity)) {
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

    @Override
    @SuppressWarnings("unchecked")
    public <T> T merge(T entity) {
        // Entity Manager를 통해 기존의 entity를 가져옴.
        final TableDefinition tableDefinition = new TableDefinition(entity.getClass());
        final T persisted = (T) find(entity.getClass(), tableDefinition.tableId().getValue(entity));

        // 기존의 entity와 새로운 entity를 비교하여 변경된 필드만 업데이트
        final List<? extends Queryable> dirtyColumns = checkDirtyColumns(entity, persisted);
        final UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder();
        updateQueryBuilder.addColumn(dirtyColumns, entity);
        updateQueryBuilder.addConditions(List.of(tableDefinition.tableId()), entity);

        final String query = updateQueryBuilder.build(entity);

        try {
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(databaseServer.getConnection());
            jdbcTemplate.execute(query);
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<? extends Queryable> checkDirtyColumns(Object newEntity, Object persisted) {
        final TableDefinition newEntityTableDefinition = new TableDefinition(newEntity.getClass());
        final List<? extends Queryable> columns = newEntityTableDefinition.queryableColumns();

        return columns.stream()
                .filter(column -> {
                    // 양쪽 다 null인 경우
                    if (!column.hasValue(newEntity) && !column.hasValue(persisted)) {
                        return false;
                    }

                    // 양쪽 다 값이 있는 경우
                    if (column.hasValue(newEntity) && column.hasValue(persisted)) {
                        return !(column.getValue(newEntity).equals(column.getValue(persisted)));
                    }

                    // 한쪽만 값이 있는 경우
                    return true;
                })
                .toList();
    }
}
