package orm.dsl.step.dml;

public interface InsertIntoValuesStep extends InsertIntoReturnStep {
    <T> InsertIntoReturnStep values(T entity);
}
