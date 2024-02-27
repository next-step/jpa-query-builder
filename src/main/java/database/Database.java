package database;

import java.sql.ResultSet;

public interface Database {
    ResultSet executeQuery(String sql);
}
