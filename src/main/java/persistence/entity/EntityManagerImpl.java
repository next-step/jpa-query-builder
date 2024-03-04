package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jakarta.persistence.Id;
import jdbc.JdbcTemplate;
import persistence.entity.exception.InvalidPrimaryKeyException;
import persistence.sql.common.DtoMapper;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.PrimaryKeyClause;
import persistence.sql.ddl.TableClause;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class EntityManagerImpl<T> implements EntityManager<T>{

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T find(Class<T> clazz, Long id) {
        String query = new SelectQueryBuilder(clazz).getFindById(id);
        return jdbcTemplate.queryForObject(query, new DtoMapper<>(clazz));
    }

    @Override
    public Object persist(Object entity) {
        Class<?> clazz = entity.getClass();
        String queryToInsert = new InsertQueryBuilder(clazz).getInsertQuery(entity);
        jdbcTemplate.execute(queryToInsert);

        String queryToSelect = new SelectQueryBuilder(clazz).getFindLastRowQuery();
        return jdbcTemplate.queryForObject(queryToSelect, new DtoMapper<>(clazz));
    }

    @Override
    public void remove(Object entity) {
        Long id = PrimaryKeyClause.primaryKeyValue(entity);
        String query = new DeleteQueryBuilder(entity.getClass()).deleteById(id);
        jdbcTemplate.execute(query);
    }
}
