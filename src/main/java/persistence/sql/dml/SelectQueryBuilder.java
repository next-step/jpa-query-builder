package persistence.sql.dml;

import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.QueryBuilder;
import static persistence.sql.constant.SqlConstant.COMMA;
import persistence.sql.meta.Column;
import persistence.sql.meta.Table;

public class SelectQueryBuilder implements QueryBuilder {

    private static final String SELECT_DEFINITION = "SELECT %s FROM %s";
    public static SelectQueryBuilder from() {
        return new SelectQueryBuilder();
    }

    private SelectQueryBuilder() {
    }

    @Override
    public String generateQuery(Object object) {

        Table table = Table.of((Class<?>) object);

        return String.format(SELECT_DEFINITION, columnsClause(table.getColumns()), table.getTableName());
    }

    private String columnsClause(List<Column> columns) {
        return columns.stream()
            .map(Column::getColumnName)
            .collect(Collectors.joining(COMMA));
    }
}
