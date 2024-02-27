package persistence.sql.dml;

import persistence.sql.mapping.Columns;
import persistence.sql.mapping.TableData;

import java.util.stream.Collectors;

public class InsertQueryBuilder {
    private final TableData table;

    public InsertQueryBuilder(Class<?> clazz) {
        this.table = TableData.from(clazz);
    }

    public String toQuery(Object entity) {
        Columns columns = Columns.createColumnsWithValue(entity.getClass(), entity);

        return String.format(
                "insert into %s (%s) values (%s)",
                table.getName(),
                columnClause(columns),
                valueClause(columns)
        );
    }

    private String columnClause(Columns columns) {
        return String.join(", ", columns.getNames());
    }

    private String valueClause(Columns columns) {
        return columns.getValues()
                .stream()
                .map(ValueUtil::getValueString)
                .collect(Collectors.joining(", "));
    }
}
