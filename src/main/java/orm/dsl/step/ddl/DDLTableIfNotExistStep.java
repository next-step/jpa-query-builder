package orm.dsl.step.ddl;

import orm.dsl.QueryExecutor;
import orm.dsl.QueryExtractor;

public interface DDLTableIfNotExistStep extends QueryExtractor, QueryExecutor {
    QueryExtractor ifNotExist();
}
