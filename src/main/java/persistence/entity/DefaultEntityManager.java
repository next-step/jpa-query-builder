package persistence.entity;


import java.sql.ResultSet;
import java.util.List;
import persistence.exception.NotFoundException;
import persistence.mapper.EntityRowsMapper;
import persistence.mapper.EntitySingleMapper;
import persistence.jdbc.JdbcTemplate;
import persistence.meta.EntityMeta;
import persistence.sql.QueryGenerator;


public class DefaultEntityManager implements EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public DefaultEntityManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        if (id == null) {
            throw new NotFoundException("id가 널이면 안 됩니다.");
        }

        final EntityMeta entityMeta = new EntityMeta(clazz);

        final String query = QueryGenerator.from(entityMeta)
                .select()
                .findById(id);

        return jdbcTemplate.queryForObject(query, new EntitySingleMapper<>(clazz));
    }

    @Override
    public Object persist(Object entity) {
        final EntityMeta entityMeta = new EntityMeta(entity.getClass());

        final String query = QueryGenerator.from(entityMeta).insert(entity);

        jdbcTemplate.execute(query);

        return entity;
    }

    @Override
    public void remove(Object entity) {
        final EntityMeta entityMeta = new EntityMeta(entity.getClass());

        final String query = QueryGenerator.from(entityMeta).delete(entityMeta.getPkValue(entity));

        jdbcTemplate.execute(query);
    }

    public <T> List<T> findList(String query, Class<T> tClass) {
        return jdbcTemplate.query(query,
                (ResultSet rs) -> new EntityRowsMapper<>(tClass).mapRow(rs));
    }

    public <T> T find(String query, Class<T> tClass) {
        return jdbcTemplate.queryForObject(query,
                (ResultSet rs) -> new EntitySingleMapper<>(tClass).mapRow(rs));
    }
}
