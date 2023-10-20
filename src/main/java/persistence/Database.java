package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database {
    ResultSet executeQuery(String sql) throws SQLException;
    void execute(String sql) throws SQLException;
}
