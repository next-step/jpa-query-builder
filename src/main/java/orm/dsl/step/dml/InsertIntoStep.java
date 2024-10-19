package orm.dsl.step.dml;

import orm.dsl.QueryExecutor;
import orm.dsl.QueryExtractor;

import java.util.List;

public interface InsertIntoStep extends QueryExtractor, QueryExecutor {
    <T> InsertIntoStep values(T entity);
    <T> InsertIntoStep values(List<T> entityList);
}
