package orm.dsl.step.dml;

import orm.dsl.QueryFetcher;

public interface SelectFromStep<E> extends WhereStep, QueryFetcher<E> {

}
