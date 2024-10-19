package orm.dsl;

public interface QueryExecutorWithReturning<T> {
    T executeWithReturning();
}
