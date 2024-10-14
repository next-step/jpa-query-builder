package jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyTableCreation() {
        final List<String> tableNames = query(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'PERSON'",
                resultSet -> resultSet.getString("TABLE_NAME")
        );

        final boolean isTableCreated = !tableNames.isEmpty();

        if (isTableCreated) {
            logger.info("Person 테이블이 성공적으로 생성되었습니다.");
        } else {
            logger.error("Person 테이블 생성에 실패했습니다.");
            throw new IllegalStateException("Person 테이블 생성에 실패했습니다.");
        }
    }
}
