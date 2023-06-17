package persistence.ddl.database;


import java.sql.ResultSet;
import java.util.List;

public interface Database {
    ResultSet executeQuery(String sql);

    List<ResultSet> query(String sql);
}
