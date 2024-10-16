package orm.dsl.step.dml;

import orm.dsl.QueryBuilder;
import orm.dsl.condition.Condition;

public interface WhereStep extends QueryBuilder {

    QueryBuilder where(Condition condition);

    QueryBuilder where(Condition... conditions);
}
