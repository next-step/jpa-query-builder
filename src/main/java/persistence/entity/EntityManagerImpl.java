package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import persistence.Person;
import persistence.sql.column.TableColumn;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.mapper.GenericRowMapper;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;
    private final Dialect dialect;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate, Dialect dialect) {
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {

        SelectQueryBuilder queryBuilder = new SelectQueryBuilder(dialect);
        SelectQueryBuilder build = queryBuilder.build(clazz);
        String query = build.findById(id);
        RowMapper<T> rowMapper = new GenericRowMapper<>(clazz, dialect);
        return jdbcTemplate.queryForObject(query, rowMapper);
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
