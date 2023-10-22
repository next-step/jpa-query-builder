package repository;

import persistence.entity.JdbcTemplate;
import persistence.meta.EntityMeta;
import persistence.sql.QueryGenerator;

public class DDLRepository<T> implements Repository<T> {
    private final JdbcTemplate jdbcTemplate;

    private final EntityMeta entityMeta;

    public DDLRepository(JdbcTemplate jdbcTemplate, Class<T> tClass) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityMeta = new EntityMeta(tClass);
    }

    public void createTable() {
        final String sql = QueryGenerator.from(entityMeta).create();
        jdbcTemplate.execute(sql);
    }

    public void dropTable() {
        final String sql = QueryGenerator.from(entityMeta).drop();
        jdbcTemplate.execute(sql);
    }
}
