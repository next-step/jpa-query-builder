package database;

import java.sql.ResultSet;

public interface Database {

    void execute(String sql);

    ResultSet executeQuery(String sql);

    void executeUpdate(String sql);

    Object executeUpdate(String sql, String pkColumnName);
}
