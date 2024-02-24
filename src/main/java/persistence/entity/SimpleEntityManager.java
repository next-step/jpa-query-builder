package persistence.entity;

import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import persistence.sql.dml.DmlGenerator;
import persistence.sql.meta.Columns;

public class SimpleEntityManager implements EntityManager {

    private final DmlGenerator dmlGenerator;
    private final JdbcTemplate jdbcTemplate;


    private SimpleEntityManager(JdbcTemplate jdbcTemplate) {
        this.dmlGenerator = DmlGenerator.from();
        this.jdbcTemplate = jdbcTemplate;
    }

    public static SimpleEntityManager from(JdbcTemplate jdbcTemplate) {
        return new SimpleEntityManager(jdbcTemplate);
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return jdbcTemplate.queryForObject(dmlGenerator.generateSelectQuery(clazz, id),
            resultSet -> new EntityRowMapper<>(clazz).mapRow(resultSet));
    }

    @Override
    public void persist(Object entity) {
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(entity));
    }

    @Override
    public void remove(Object entity) {
        Columns columns = Columns.from(entity.getClass().getDeclaredFields());
        jdbcTemplate.execute(dmlGenerator.generateDeleteQuery(entity.getClass(),
            columns.getIdValue(entity)));
    }
}
