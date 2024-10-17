package jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Table;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);

    private final Connection connection;

    public JdbcTemplate(final Connection connection) {
        this.connection = connection;
    }

    public void execute(final String sql) {
        try (final Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T queryForObject(final String sql, final RowMapper<T> rowMapper) {
        final List<T> results = query(sql, rowMapper);
        if (results.size() != 1) {
            throw new RuntimeException("Expected 1 result, got " + results.size());
        }
        return results.get(0);
    }

    public <T> List<T> query(final String sql, final RowMapper<T> rowMapper) {
        try (final ResultSet resultSet = connection.prepareStatement(sql).executeQuery()) {
            final List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }
            return result;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyTableCreation(final Class<?> clazz) {
        final boolean isTableCreated = isTableCreated(clazz);
        if (!isTableCreated) throw new IllegalStateException("테이블 생성에 실패했습니다.");
        logger.info("테이블이 성공적으로 생성되었습니다.");
    }

    public void verifyTableDeletion(final Class<?> clazz) {
        final boolean isTableCreated = isTableCreated(clazz);
        if (isTableCreated) throw new IllegalStateException("테이블 삭제에 실패했습니다.");
        logger.info("테이블이 성공적으로 삭제되었습니다.");
    }

    private boolean isTableCreated(final Class<?> clazz) {
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
