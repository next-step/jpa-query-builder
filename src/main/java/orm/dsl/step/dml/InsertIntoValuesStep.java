package orm.dsl.step.dml;

public interface InsertIntoValuesStep<E> extends ReturningStep<E> {
    ReturningStep<E> value(E entity);
}
