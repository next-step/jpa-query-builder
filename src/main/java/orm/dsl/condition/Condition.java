package orm.dsl.condition;

public interface Condition {
    Condition or(Condition condition);

    Condition and(Condition condition);
}
