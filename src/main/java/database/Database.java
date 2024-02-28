package database;

import java.util.HashMap;
import java.util.List;

public interface Database {

    void execute(String sql);

    HashMap<String, Object> executeQueryForObject(String sql);
}
