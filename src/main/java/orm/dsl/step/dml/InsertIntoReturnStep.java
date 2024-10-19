package orm.dsl.step.dml;

import orm.dsl.QueryExecutor;
import orm.dsl.QueryExecutorWithReturning;
import orm.dsl.QueryExtractor;

public interface InsertIntoReturnStep extends QueryExtractor, QueryExecutor {

    /**
     * 복합키인 경우 예외 발생,
     * Auto Increment key값이 포함된 엔티티를 반환
     * @return
     * @param <T>
     */
    <T> QueryExecutorWithReturning<T> returnWithAIkey();
}
