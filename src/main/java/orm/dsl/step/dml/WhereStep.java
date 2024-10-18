package orm.dsl.step.dml;

import orm.dsl.condition.Condition;

public interface WhereStep extends ConditionStep {

    ConditionStep where(Condition condition);

    ConditionStep where(Condition... conditions);
}
