package orm.dsl.step.dml;

import orm.dsl.QueryBuilder;

import java.util.List;

public interface InsertIntoStep extends QueryBuilder {
    <T> InsertIntoStep values(T entity);
    <T> InsertIntoStep values(List<T> entityList);
}
