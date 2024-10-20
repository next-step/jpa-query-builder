package persistence.entity;

import java.sql.Connection;
import java.util.List;
import jdbc.JdbcTemplate;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.query.DeleteQuery;
import persistence.sql.dml.query.InsertQuery;
import persistence.sql.dml.query.SelectQuery;
import persistence.sql.dml.query.WhereCondition;
import persistence.sql.dml.query.builder.DeleteQueryBuilder;
import persistence.sql.dml.query.builder.InsertQueryBuilder;
import persistence.sql.dml.query.builder.SelectQueryBuilder;

public class EntityManagerImpl implements EntityManager {

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        SelectQuery query = new SelectQuery(clazz);
        String queryString = SelectQueryBuilder.builder(new H2Dialect())
                .select(query.columnNames())
                .from(query.tableName())
                .where(List.of(new WhereCondition("id", "=", id)))
                .build();
        return jdbcTemplate.queryForObject(queryString, new EntityRowMapper<>(clazz));
    }

    @Override
    public void persist(Object entity) {
        InsertQuery query = new InsertQuery(entity);
        String queryString = InsertQueryBuilder.builder(new H2Dialect())
                .insert(query.tableName(), query.columns())
                .values(query.columns())
                .build();
       jdbcTemplate.execute(queryString);
    }

    @Override
    public void remove(Object entity) {
        DeleteQuery query = new DeleteQuery(entity.getClass());
        String queryString = DeleteQueryBuilder.builder(new H2Dialect())
                .delete(query.tableName())
                .build();

    }

}
