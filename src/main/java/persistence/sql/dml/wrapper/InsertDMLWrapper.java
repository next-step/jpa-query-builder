package persistence.sql.dml.wrapper;

import persistence.sql.dml.value.GeneralValue;
import persistence.sql.dml.value.IdValue;

import java.util.List;
import java.util.stream.Collectors;

public class InsertDMLWrapper implements DMLWrapper {

    @Override
    public String wrap(String tableName, IdValue idValue, List<GeneralValue> generalValues) {
        return "INSERT INTO " + tableName + "( " +
                idValue.getColumnName() + ", " +
                generalValues.stream().map(GeneralValue::getColumnName).collect(Collectors.joining(", ")) +
                " ) VALUES ( " +
                idValue.getValue() +
                " " +
                generalValues.stream().map(GeneralValue::getValue).collect(Collectors.joining(", ")) +
                " )";
    }
}
