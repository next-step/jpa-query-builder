package orm.dsl.step.dml;

import orm.QueryExecutor;
import orm.dsl.QueryBuilder;

import java.util.List;

public interface InsertIntoStep extends QueryBuilder, QueryExecutor {
    <T> InsertIntoStep values(T entity);
    <T> InsertIntoStep values(List<T> entityList);
}
