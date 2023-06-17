package persistence.ddl.database;


import jdbc.RowMapper;

import java.sql.ResultSet;
import java.util.List;

public interface Database<T> {
    ResultSet executeQuery(String sql);

    List<T> query(String sql, RowMapper<T> rowMapper);
}
