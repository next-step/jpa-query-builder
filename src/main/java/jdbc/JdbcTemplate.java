package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
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

    /**
     * getLastIndex 수행 메소드
     */
    public Long executeAndReturnKey(final String sql) {
        ResultSet resultSet = null;

        try (final Statement statement = connection.createStatement()) {
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            Long seq = null;
            if (resultSet.next()) {
                seq = resultSet.getLong("id");
            }
            return seq;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

//    public <T> List<T> queryLastIndex(final String sql, final RowMapper<T> rowMapper) {
//        try {
//
//            final Statement statement = connection.createStatement();
//            final ResultSet resultSet = connection.prepareStatement(sql, statement.getGeneratedKeys().).executeQuery()
//
//            final List<T> result = new ArrayList<>();
//            while (resultSet.next()) {
//                result.add(rowMapper.mapRow(resultSet));
//            }
//            return result;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
