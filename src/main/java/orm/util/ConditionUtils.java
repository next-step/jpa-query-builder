package orm.util;

import orm.dsl.condition.Condition;
import orm.dsl.step.dml.EqualCondition;

public class ConditionUtils {

    public static Condition eq(String field, Object value) {
        return new EqualCondition(field, value);
    }
}
