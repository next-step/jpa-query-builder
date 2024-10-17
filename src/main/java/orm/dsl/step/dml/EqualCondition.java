package orm.dsl.step.dml;

import orm.TableField;
import orm.dsl.condition.AbstractCondition;

import java.util.Objects;

/**
 * Where 조건절 중 '=' 연산자를 담당한다.
 */
public class EqualCondition extends AbstractCondition {

    private final TableField field;
    private final Object value;
    private final String dbColumn;

    public EqualCondition(String field, Object value) {
        this.field = null;
        this.value = value;
        this.dbColumn = field;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EqualCondition that = (EqualCondition) object;
        return Objects.equals(field, that.field) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, value);
    }

    @Override
    public String toString() {
        return dbColumn + " = " + value + getAppendedConditions();
    }
}
