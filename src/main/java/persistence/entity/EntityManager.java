package persistence.entity;

import jdbc.JdbcTemplate;

import java.sql.Connection;

public class EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManager(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    public <T> T find(Class<T> clazz, Long id) {

    }


    public void persist(Object object) {

    }

    public void remove(Object object) {

    }
}
