package orm.dsl.step.dml;

public interface InsertIntoValuesStep<E> extends InsertIntoReturnStep<E> {
    InsertIntoReturnStep<E> value(E entity);
}
