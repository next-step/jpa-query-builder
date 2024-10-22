package persistence.entity;

import jakarta.persistence.Id;
import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.delete.DeleteQueryBuilder;
import persistence.sql.dml.insert.InsertQueryBuilder;
import persistence.sql.dml.select.SelectQueryBuilder;
import persistence.sql.dml.update.UpdateQueryBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;

public class EntityManagerImpl<T, U> implements EntityManager<T, U> {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T find(Class<T> clazz, U id) {
        if (id == null) {
            return null;
        }
        String findByIdQuery = SelectQueryBuilder.generateQuery(clazz, String.valueOf(id));
        return jdbcTemplate.queryForObject(findByIdQuery, new EntityRowMapper<>(clazz));
    }

    @Override
    public void persist(T newEntity) {
        U idValue = getIdValue(newEntity);
        T originalEntity = find((Class<T>) newEntity.getClass(), idValue);

        if (idValue == null || originalEntity == null) {
            String insertQuery = InsertQueryBuilder.generateQuery(newEntity);
            jdbcTemplate.execute(insertQuery);
        } else {
            String updateQueyr = UpdateQueryBuilder.generateQuery(newEntity);
            jdbcTemplate.execute(updateQueyr);
        }
    }

    @Override
    public void remove(T entity) {
        String deleteQuery = DeleteQueryBuilder.generateQuery(entity.getClass(), entity);
        jdbcTemplate.execute(deleteQuery);
    }

    private U getIdValue(T entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        Field idField = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findAny().orElseThrow();
        idField.setAccessible(true);
        try {
            return (U) idField.get(entity);
        } catch (IllegalAccessException e) {
            logger.error("Inappropriate entity class!");
            throw new RuntimeException(e);
        }
    }
}
