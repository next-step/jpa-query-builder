package database;

import jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class ObjectRowMapper implements RowMapper<HashMap<String, Object>> {
    @Override
    public HashMap<String, Object> mapRow(ResultSet resultSet) throws SQLException {
        HashMap<String, Object> map = new HashMap<>();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int count = metaData.getColumnCount();

        for (int i = 1; i <= count; i++) {
            String columnName = metaData.getColumnName(i);
            Object columnValue = resultSet.getObject(columnName);

            String lowerCaseColumnName = columnName.toLowerCase();
            map.put(lowerCaseColumnName, columnValue);
        }

        return map;
    }
}
