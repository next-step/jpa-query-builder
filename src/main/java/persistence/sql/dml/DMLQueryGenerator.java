package persistence.sql.dml;

import persistence.sql.dialect.Dialect;
import persistence.sql.extractor.ColumnData;
import persistence.sql.extractor.ColumnExtractor;
import persistence.sql.extractor.TableData;
import persistence.sql.extractor.TableExtractor;

import java.util.List;
import java.util.stream.Collectors;

public class DMLQueryGenerator {
    private final TableData table;

    public DMLQueryGenerator(Class<?> clazz, Dialect dialect) {
        this.table = new TableExtractor(clazz).createTable();
    }

    public String generateInsertQuery(Object entity) {
        List<ColumnData> columns = new ColumnExtractor(entity.getClass()).createColumnsWithValue(entity);

        return String.format(
                "insert into %s (%s) values (%s)",
                table.getName(),
                columnsClause(columns),
                valueClause(columns)
        );
    }

    private String columnsClause(List<ColumnData> columns) {
        return columns.stream()
                .map(ColumnData::getName)
                .collect(Collectors.joining(", "));
    }

    private String valueClause(List<ColumnData> columns) {
        return columns.stream()
                .map(column -> valueToString(column.getValue()))
                .collect(Collectors.joining(", "));
    }

    private String valueToString(Object value){
        if(value == null){
            return "null";
        }
        return value.toString();
    }
}
