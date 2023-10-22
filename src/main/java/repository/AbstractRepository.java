package repository;

import persistence.entity.JdbcTemplate;
import persistence.meta.EntityMeta;

public abstract class AbstractRepository<T> {
    protected final JdbcTemplate jdbcTemplate;
    protected final Class<T> tClass;
    protected final EntityMeta entityMeta;

    protected AbstractRepository(JdbcTemplate jdbcTemplate, Class<T> tClass) {
        this.tClass = tClass;
        this.entityMeta = new EntityMeta(tClass);
        this.jdbcTemplate = jdbcTemplate;
    }
}
