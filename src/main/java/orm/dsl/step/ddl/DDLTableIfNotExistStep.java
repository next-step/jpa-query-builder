package orm.dsl.step.ddl;

import orm.QueryExecutor;
import orm.dsl.QueryBuilder;

public interface DDLTableIfNotExistStep extends QueryBuilder, QueryExecutor {
    QueryBuilder ifNotExist();
}
