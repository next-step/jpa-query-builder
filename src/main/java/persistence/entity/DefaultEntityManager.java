package persistence.entity;


import java.util.List;
import persistence.exception.NotFoundException;
import persistence.mapper.RowMapper;
import persistence.meta.EntityMeta;
import persistence.sql.QueryGenerator;


public class DefaultEntityManager implements EntityManager {
    private final JdbcTemplate jdbcTemplate;
    private final EntityMeta entityMeta;

    public DefaultEntityManager(JdbcTemplate jdbcTemplate, EntityMeta entityMeta) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityMeta = entityMeta;
    }

    @Override
    public Object persist(Object entity) {
        final String query = QueryGenerator.from(entityMeta).insert(entity);

        jdbcTemplate.execute(query);

        return entity;
    }


    @Override
    public void remove(Object entity) {
        final String query = QueryGenerator.from(entityMeta).delete(entityMeta.getPkValue(entity));

        jdbcTemplate.execute(query);
    }

    @Override
    public <T> T find(Class<T> clazz, Object id) {
        if (id == null) {
            throw new NotFoundException("id가 널이면 안 됩니다.");
        }

        final String query = QueryGenerator.from(entityMeta)
                .select()
                .findById(id);

        return jdbcTemplate.queryForObject(query, getRowMapper(clazz));
    }

    @Override
    public <T> List<T> findAll(Class<T> tClass) {
        final String query = QueryGenerator.from(entityMeta)
                .select()
                .findAll();

        return jdbcTemplate.query(query, getRowMapper(tClass));
    }


    private <T> RowMapper<T> getRowMapper(Class<T> tClass) {
        return new EntityPersister<>(tClass);
    }
}
