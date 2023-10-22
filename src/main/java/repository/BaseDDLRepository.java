package repository;

import persistence.entity.JdbcTemplate;
import persistence.sql.QueryGenerator;

public class BaseDDLRepository<T> extends AbstractRepository<T> implements DDLRepository<T> {

    protected BaseDDLRepository(JdbcTemplate jdbcTemplate, Class<T> tClass) {
        super(jdbcTemplate, tClass);
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
