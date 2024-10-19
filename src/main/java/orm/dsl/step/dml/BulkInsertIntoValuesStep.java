package orm.dsl.step.dml;

import java.util.List;

public interface BulkInsertIntoValuesStep extends BulkInsertIntoReturnStep {
    <T> BulkInsertIntoValuesStep values(List<T> entityList);
}
