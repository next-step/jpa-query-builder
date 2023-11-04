package persistence.sql.dialect;

import persistence.sql.dml.delete.DeleteQuery;
import persistence.sql.dml.insert.InsertQuery;
import persistence.sql.dml.select.SelectQuery;
import persistence.sql.dml.where.WhereQuery;

public interface Dialect {
    String insertBuilder(InsertQuery insertQuery);

    String selectBuilder(SelectQuery selectQuery);

    String selectConditionBuilder(SelectQuery selectQuery, WhereQuery whereQuery);

    String deleteBuilder(DeleteQuery deleteQuery, WhereQuery whereQuery);
}
