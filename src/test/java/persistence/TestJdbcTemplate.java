package persistence;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;

import jakarta.persistence.Table;
import java.sql.Connection;
import java.util.List;

public class TestJdbcTemplate extends JdbcTemplate {
    public TestJdbcTemplate(final Connection connection) {
        super(connection);
    }

    @Override
    public void execute(final String sql) {
        super.execute(sql);
    }

    @Override
    public <T> T queryForObject(final String sql, final RowMapper<T> rowMapper) {
        return super.queryForObject(sql, rowMapper);
    }

    @Override
    public <T> List<T> query(final String sql, final RowMapper<T> rowMapper) {
        return super.query(sql, rowMapper);
    }

    public boolean doesTableExist(final Class<?> clazz) {
        final String tableName = getTableName(clazz);
        final List<String> tableNames = query(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = '%s'".formatted(tableName),
                resultSet -> resultSet.getString("TABLE_NAME")
        );
        return !tableNames.isEmpty();
    }

    private String getTableName(final Class<?> clazz) {
        final Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name().toUpperCase();
        }
        return clazz.getSimpleName().toUpperCase();
    }
}
