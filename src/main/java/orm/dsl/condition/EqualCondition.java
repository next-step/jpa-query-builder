package orm.dsl.condition;

import orm.TableField;

/**
 * Where 조건절 중 '=' 연산자를 담당한다.
 */
public class EqualCondition extends AbstractCondition {

    private final TableField field;
    private final String dbColumn;

    public EqualCondition(String field, Object value) {
        super(value);
        this.field = null;
        this.dbColumn = field;
    }

    @Override
    public String toString() {
        return dbColumn + " = " + getSqlAwareValue() + getAppendedConditions();
    }
}
