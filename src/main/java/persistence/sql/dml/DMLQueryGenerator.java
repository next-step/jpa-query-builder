package persistence.sql.dml;

import persistence.sql.mapping.*;

import java.util.stream.Collectors;

public class DMLQueryGenerator {
    private final TableData table;

    public DMLQueryGenerator(Class<?> clazz) {
        this.table = new TableExtractor(clazz).createTable();
    }

    public String generateInsertQuery(Object entity) {
        Columns columns = Columns.createColumnsWithValue(entity.getClass(), entity);

        return String.format(
                "insert into %s (%s) values (%s)",
                table.getName(),
                columnClause(columns),
                valueClause(columns)
        );
    }

    public String generateSelectQuery(WhereBuilder whereBuilder) {
        StringBuilder query = new StringBuilder();
        query.append("select ");
        query.append(selectClause());
        query.append(" from ");
        query.append(table.getName());

        if(whereBuilder.isEmpty()) {
            return query.toString();
        }

        query.append(" where ");
        query.append(whereBuilder.toClause());

        return query.toString();
    }

    public String generateDeleteQuery(WhereBuilder whereBuilder) {
        StringBuilder query = new StringBuilder();
        query.append("delete from ");
        query.append(table.getName());

        if(whereBuilder.isEmpty()) {
            return query.toString();
        }

        query.append(" where ");
        query.append(whereBuilder.toClause());

        return query.toString();
    }

    private String selectClause() {
        return "*";
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
