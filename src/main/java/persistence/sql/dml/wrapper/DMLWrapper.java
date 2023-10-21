package persistence.sql.dml.wrapper;

import persistence.sql.dml.value.GeneralValue;
import persistence.sql.dml.value.IdValue;

import java.util.List;

public interface DMLWrapper {
    String wrap(String tableName, IdValue idValue, List<GeneralValue> generalValues);
}
