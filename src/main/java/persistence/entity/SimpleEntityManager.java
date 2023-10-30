package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.ddl.EntityMetadata;

public class SimpleEntityManager implements EntityManager {

    private final JdbcTemplate jdbcTemplate;

    public SimpleEntityManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        // TOD type에 맞는 EntityMetadata를 반환해 주는 객체하나 만들자.



    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }

}
