package persistence;

import jdbc.JdbcTemplate;
import jdbc.PersonRowMapper;
import persistence.sql.ddl.DDLQueryBuilder;
import persistence.sql.dml.DMLQueryBuilder;

public class EntityManagerImpl implements EntityManager {

    private DDLQueryBuilder ddlQueryBuilder;
    private DMLQueryBuilder dmlQueryBuilder;
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        ddlQueryBuilder = DDLQueryBuilder.getInstance();
        dmlQueryBuilder = DMLQueryBuilder.getInstance();
    }


    @Override
    public <T> void createTable(Class<T> tClass) {
        jdbcTemplate.execute(ddlQueryBuilder.createTableQuery(tClass));
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        return (T) jdbcTemplate.queryForObject(dmlQueryBuilder.selectByIdQuery(clazz, Id), new PersonRowMapper());
    }

    @Override
    public <T> T persist(T entity) {
        jdbcTemplate.execute(dmlQueryBuilder.insertSql(entity));
        return entity;
    }

    @Override
    public void remove(Object entity) {
        jdbcTemplate.execute(dmlQueryBuilder.deleteSql(entity));
    }
}
