package orm.dsl.condition;

public class NoCondition extends AbstractCondition {

    public NoCondition() {
        super(null);
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Condition and(Condition condition) {
        throw new UnsupportedOperationException("NoCondition does not support 'AND' operation");
    }

    @Override
    public Condition or(Condition condition) {
        throw new UnsupportedOperationException("NoCondition does not support 'or' operation");
    }
}
