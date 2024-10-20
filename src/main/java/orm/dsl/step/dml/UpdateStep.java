package orm.dsl.step.dml;

public interface UpdateStep<E> extends WhereStep {
    ConditionStep byId();
}
