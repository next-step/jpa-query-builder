package orm.dsl.step.dml;

import orm.dsl.condition.Condition;

public interface WhereForFetchStep<E> extends ConditionForFetchStep<E> {

    ConditionForFetchStep<E> where(Condition condition);

    ConditionForFetchStep<E> where(Condition... conditions);
}
