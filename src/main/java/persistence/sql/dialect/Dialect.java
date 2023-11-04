package persistence.sql.dialect;

import persistence.sql.dml.insert.InsertQuery;
import persistence.sql.dml.select.SelectQuery;

public interface Dialect {
    String insertBuilder(InsertQuery insertQuery);

    String selectBuilder(SelectQuery selectQuery);
}
