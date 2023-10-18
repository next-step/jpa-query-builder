package persistence;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.h2.tools.SimpleResultSet;
import persistence.sql.dml.SelectQuery;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class RepositoryImpl<T extends RowMapper<T>> implements Repository<T> {

    private final RowMapper<? extends T> t;
    private JdbcTemplate jdbcTemplate;

    public RepositoryImpl(RowMapper<? extends T> t, Connection connection) {
        this.t = t;
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public List<T> findAll() {
        String query = SelectQuery.create(t.getClass(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        RowMapper<T> rowMapper = mapRow(new SimpleResultSet());

        return jdbcTemplate.query(query, rowMapper);
    }
}
