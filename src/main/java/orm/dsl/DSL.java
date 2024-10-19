package orm.dsl;

import orm.dsl.condition.Condition;
import orm.dsl.condition.EqualCondition;

/**
 * DSL 생성에 필요한 모든 유틸 클래스
 */
public class DSL {

    public static Condition eq(String field, Object value) {
        return new EqualCondition(field, value);
    }
}
