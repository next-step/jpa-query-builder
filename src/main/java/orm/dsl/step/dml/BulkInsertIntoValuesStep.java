package orm.dsl.step.dml;

import orm.dsl.QueryExecutor;
import orm.dsl.QueryExtractor;

import java.util.List;

public interface BulkInsertIntoValuesStep extends QueryExtractor, QueryExecutor {
    <T> BulkInsertIntoValuesStep values(List<T> entityList);
}
