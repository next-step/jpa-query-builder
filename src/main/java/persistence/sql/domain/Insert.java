package persistence.sql.domain;

import java.util.List;
import java.util.function.Function;

import static persistence.sql.CommonConstant.COLUMN_SEPARATOR;

public class Insert {

    private final String columnClause;

    private final String valueClause;

    public Insert(DatabaseTable table) {
        List<ColumnOperation> columns = table.getAllColumns();
        this.columnClause = getClause(columns, ColumnOperation::getJdbcColumnName);
        this.valueClause = getClause(columns, ColumnOperation::getColumnValue);
    }

    private String getClause(List<ColumnOperation> columns, Function<ColumnOperation, String> function) {
        return columns.stream()
                .filter(ColumnOperation::hasColumnValue)
                .map(function)
                .reduce((columnA, columnB) -> String.join(COLUMN_SEPARATOR, columnA, columnB))
                .orElseThrow(IllegalStateException::new);
    }

    public String getColumnClause() {
        return columnClause;
    }

    public String getValueClause() {
        return valueClause;
    }
}
