package persistence.entity;

import jakarta.persistence.Id;
import jdbc.EntityMapper;
import jdbc.JdbcTemplate;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.dml.WhereClauseBuilder;
import persistence.sql.metadata.Value;
import persistence.sql.metadata.Values;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SimpleEntityManager implements EntityManager{
    private final JdbcTemplate jdbcTemplate;
    public SimpleEntityManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        Values values = new Values(Arrays.stream(clazz.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .map(x -> new Value(x, String.valueOf(id)))
                .collect(Collectors.toList()));

        String query = new SelectQueryBuilder(clazz, new WhereClauseBuilder(values)).buildFindByIdQuery();
        return jdbcTemplate.queryForObject(query, new EntityMapper<>(clazz));
    }

    @Override
    public void persist(Object entity) {
        String query = new InsertQueryBuilder(entity).buildQuery();
        jdbcTemplate.execute(query);
    }

    @Override
    public void remove(Object entity) {

    }
}
