package persistence.sql.dml;

import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.QueryBuilder;
import static persistence.sql.constant.SqlConstant.COMMA;
import persistence.sql.meta.Column;
import persistence.sql.meta.Table;

public class InsertQueryBuilder implements QueryBuilder {

    private static final String INSERT_DEFINITION = "INSERT INTO %s (%s) VALUES (%s)";

    public static InsertQueryBuilder from() {
        return new InsertQueryBuilder();
    }

    private InsertQueryBuilder() {
    }

    @Override
    public String generateQuery(Object object) {
        Table table = Table.from(object.getClass());

        return String.format(INSERT_DEFINITION, table.getTableName(),
            columnsClause(table.getColumns()), valueClause(table.getColumns(), object));
    }

    private String columnsClause(List<Column> columns) {
        return columns.stream()
            .filter(Column::isInsertable)
            .map(Column::getColumnName)
            .collect(Collectors.joining(COMMA));
    }

    private String valueClause(List<Column> columns, Object object) {
        return columns.stream()
            .filter(Column::isInsertable)
            .map(column -> column.getFieldValue(object))
            .collect(Collectors.joining(COMMA));
    }
}
