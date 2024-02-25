package persistence.sql.domain;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static persistence.sql.CommonConstant.COLUMN_SEPARATOR;

public class DatabaseColumns {

    private static final String AND = " and ";

    private final List<DatabaseColumn> columns;

    public DatabaseColumns(List<DatabaseColumn> columns) {
        this.columns = columns;
    }

    public String getIdColumnName() {
        return columns.stream()
                .filter(column -> column instanceof DatabasePrimaryColumn)
                .map(DatabaseColumn::getName)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public String whereClause() {
        return columns.stream()
                .map(DatabaseColumn::whereClause)
                .reduce((columnA, columnB) -> String.join(AND, columnA, columnB))
                .orElseThrow(IllegalStateException::new);
    }


    public String columnClause() {
        return insertClause(DatabaseColumn::getName);
    }

    public String valueClause() {
        return insertClause(DatabaseColumn::getValue);
    }

    private String insertClause(Function<DatabaseColumn, String> convert) {
        return columns.stream()
                .filter(this::notAutoIncrementColumn)
                .map(convert)
                .reduce((columnA, columnB) -> String.join(COLUMN_SEPARATOR, columnA, columnB))
                .orElseThrow(IllegalStateException::new);
    }

    private boolean notAutoIncrementColumn(DatabaseColumn column) {
        if (column instanceof DatabasePrimaryColumn) {
            DatabasePrimaryColumn primaryColumn = (DatabasePrimaryColumn) column;
            return primaryColumn.getValue() != null;
        }
        return true;
    }

    public List<DatabaseColumn> getColumns() {
        return columns.stream()
                .map(DatabaseColumn::copy)
                .collect(Collectors.toList());
    }
}
