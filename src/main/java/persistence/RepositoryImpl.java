package persistence;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.h2.tools.SimpleResultSet;
import persistence.sql.dml.SelectQuery;

import java.sql.Connection;
import java.util.List;

public abstract class RepositoryImpl<T extends RowMapper<T>> implements Repository<T> {

    private final RowMapper<? extends T> t;
    private JdbcTemplate jdbcTemplate;

    public RepositoryImpl(RowMapper<? extends T> t, Connection connection) {
        this.t = mapRow(new SimpleResultSet());
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public List<T> findAll() {
        String query = SelectQuery.create(t.getClass(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        return (List<T>) jdbcTemplate.query(query, t);
    }

    @Override
    public <R> T findById(R r) {
        String query = SelectQuery.create(t.getClass(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        return jdbcTemplate.queryForObject(query, t);
    }
}
