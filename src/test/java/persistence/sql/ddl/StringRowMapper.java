package persistence.sql.ddl;

import jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet resultSet) throws SQLException {
        return resultSet.getString(1);
    }
}
