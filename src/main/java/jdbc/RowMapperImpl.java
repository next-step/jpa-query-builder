package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RowMapperImpl implements RowMapper<Object> {

    @Override
    public Object mapRow(ResultSet resultSet) throws SQLException {
        System.out.println("1 >> " + resultSet.getString(1));
        System.out.println("2 >> " + resultSet.getString(2));
        return null;
    }
}
