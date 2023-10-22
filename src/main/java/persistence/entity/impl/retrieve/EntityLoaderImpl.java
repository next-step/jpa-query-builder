package persistence.entity.impl.retrieve;

import java.sql.Connection;
import jdbc.JdbcTemplate;
import persistence.entity.ResultSetMapper;
import persistence.sql.dialect.ColumnType;

public class EntityLoaderImpl<T> implements EntityLoader<T> {

    private final JdbcTemplate jdbcTemplate;
    private final ResultSetMapper<T> resultSetMapper;

    public EntityLoaderImpl(Class<T> clazz, Connection connection, ColumnType columnType) {
        this.jdbcTemplate = new JdbcTemplate(connection);
        this.resultSetMapper = new ResultSetMapper<>(clazz, columnType);
    }

    @Override
    public T load(String selectSql) {
        return jdbcTemplate.queryForObject(selectSql, resultSetMapper);
    }
}
