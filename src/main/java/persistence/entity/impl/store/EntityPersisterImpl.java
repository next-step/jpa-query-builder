package persistence.entity.impl.store;

import java.sql.Connection;
import jdbc.JdbcTemplate;

public class EntityPersisterImpl implements EntityPersister {

    private final JdbcTemplate jdbcTemplate;

    public EntityPersisterImpl(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public void store(String insertSql) {
        jdbcTemplate.execute(insertSql);
    }

    @Override
    public void delete(String deleteSql) {
        jdbcTemplate.execute(deleteSql);
    }


}
