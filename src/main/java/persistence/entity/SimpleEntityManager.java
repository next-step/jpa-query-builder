package persistence.entity;

import domain.mapper.RowMapperImpl;
import jdbc.JdbcTemplate;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.dml.DmlQueryBuilder;

public class SimpleEntityManager implements EntityManager {

    private final JdbcTemplate jdbcTemplate;
    private final DdlQueryBuilder ddlQueryBuilder;
    private final DmlQueryBuilder dmlQueryBuilder;

    public SimpleEntityManager(JdbcTemplate jdbcTemplate, DdlQueryBuilder ddlQueryBuilder,
                               DmlQueryBuilder dmlQueryBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.ddlQueryBuilder = ddlQueryBuilder;
        this.dmlQueryBuilder = dmlQueryBuilder;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return jdbcTemplate.queryForObject(dmlQueryBuilder.findByIdQuery(clazz, id), new RowMapperImpl<>(clazz));
    }

    @Override
    public Object persist(Object entity) {
        return jdbcTemplate.executeAndReturnKey(dmlQueryBuilder.insertQuery(entity));
    }

    @Override
    public void remove(Object entity) {
        jdbcTemplate.execute(dmlQueryBuilder.deleteByIdQuery(entity));
    }
}
