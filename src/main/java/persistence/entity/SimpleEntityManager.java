package persistence.entity;

import jakarta.persistence.Id;
import jdbc.EntityMapper;
import jdbc.JdbcTemplate;
import persistence.sql.dml.SelectQueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleEntityManager implements EntityManager{
    private final JdbcTemplate jdbcTemplate;
    public SimpleEntityManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        String query = new SelectQueryBuilder(clazz, Arrays.stream(clazz.getDeclaredFields()).filter(x -> x.isAnnotationPresent(Id.class)).map(x->x.getName()).collect(Collectors.toList()), List.of(Id.toString())).buildQuery();
        return jdbcTemplate.queryForObject(query, new EntityMapper<>(clazz));
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
