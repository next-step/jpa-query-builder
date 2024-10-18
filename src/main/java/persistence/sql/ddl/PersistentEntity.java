package persistence.sql.ddl;

import jdbc.JdbcTemplate;

public class PersistentEntity {

    private final JdbcTemplate jdbcTemplate;

    public PersistentEntity(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable(Class<?> entityClass) {
        String sql = new CreateTableQuery(entityClass).generateQuery();
        jdbcTemplate.execute(sql);
    }

    public void dropTable(Class<?> entityClass) {
        String sql = new DropTableQuery(entityClass).generateQuery();
        jdbcTemplate.execute(sql);
    }

}
