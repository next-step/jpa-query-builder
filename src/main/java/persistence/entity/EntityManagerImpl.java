package persistence.entity;

import jakarta.persistence.GenerationType;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import persistence.sql.column.IdColumn;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.mapper.GenericRowMapper;

import java.lang.reflect.Field;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;
    private final Dialect dialect;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate, Dialect dialect) {
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {

        SelectQueryBuilder queryBuilder = new SelectQueryBuilder(dialect);
        SelectQueryBuilder build = queryBuilder.build(clazz);
        String query = build.findById(id);
        RowMapper<T> rowMapper = new GenericRowMapper<>(clazz, dialect);
        return jdbcTemplate.queryForObject(query, rowMapper);
    }

    @Override
    public Object persist(Object entity) {
        IdColumn idColumn = new IdColumn(entity.getClass().getDeclaredFields(), dialect);

        GenerationType generationType = idColumn.getIdGeneratedStrategy().getGenerationType();
        if (!dialect.getIdGeneratedStrategy(generationType).isAutoIncrement()) {
            executeQuery(entity);
            return entity;
        }

        Field idField = getIdField(entity, idColumn);

        if (getIdValue(entity, idField) == null) {
            setIdValue(entity, idField, 1L);
        }

        executeQuery(entity);

        return entity;
    }

    private Long getIdValue(Object entity, Field idField) {
        Long idValue;
        try {
            idValue = (Long) idField.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return idValue;
    }

    private Field getIdField(Object entity, IdColumn idColumn) {
        Field idField;
        try {
            idField = entity.getClass().getDeclaredField(idColumn.getName());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        idField.setAccessible(true);
        return idField;
    }

    private void setIdValue(Object entity, Field idField, long idValue) {
        try {
            idField.set(entity, idValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeQuery(Object entity) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(dialect);
        String insertQuery = insertQueryBuilder.build(entity);
        jdbcTemplate.execute(insertQuery);
    }

    @Override
    public void remove(Object entity) {
        DeleteQueryBuilder build = new DeleteQueryBuilder(dialect).build(entity);
        IdColumn idColumn = new IdColumn(entity.getClass().getDeclaredFields(), dialect);
        Field idField = getIdField(entity, idColumn);
        Long id = getIdValue(entity, idField);
        String deleteQuery = build.deleteById(id);

        jdbcTemplate.execute(deleteQuery);
    }
}
