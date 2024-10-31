package persistence.entity;

import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.delete.DeleteQueryBuilder;
import persistence.sql.dml.insert.InsertQueryBuilder;
import persistence.sql.dml.select.SelectQueryBuilder;
import persistence.sql.dml.update.UpdateQueryBuilder;

public class EntityManagerImpl implements EntityManager {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object find(Class<?> clazz, Long id) {
        if (id == null) {
            return null;
        }
        String findByIdQuery = SelectQueryBuilder.generateQuery(clazz, id);
        return jdbcTemplate.queryForObject(findByIdQuery, new EntityRowMapper<>(clazz));
    }

    @Override
    public void persist(Object newEntity) {
        Long idValue = EntityUtils.getIdValue(newEntity);
        Object originalEntity = find(newEntity.getClass(), idValue);

        if (idValue == null || originalEntity == null) {
            String insertQuery = InsertQueryBuilder.generateQuery(newEntity);
            jdbcTemplate.execute(insertQuery);
        } else {
            String updateQuery = UpdateQueryBuilder.generateQuery(newEntity);
            jdbcTemplate.execute(updateQuery);
        }
    }

    @Override
    public void remove(Object entity) {
        String deleteQuery = DeleteQueryBuilder.generateQuery(entity.getClass(), entity);
        jdbcTemplate.execute(deleteQuery);
    }
}
