package persistence.ddl.database;

import java.util.List;

public interface Database<T> {

    T executeQuery(String sql);

    List<T> query(String sql);

    void execute(String sql);
}
