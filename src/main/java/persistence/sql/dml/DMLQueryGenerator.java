package persistence.sql.dml;

import persistence.sql.mapping.*;

import java.util.List;
import java.util.stream.Collectors;

public class DMLQueryGenerator {
    private final TableData table;
    private String columnClause;

    public DMLQueryGenerator(Class<?> clazz) {
        this.table = new TableExtractor(clazz).createTable();
    }

    public String generateInsertQuery(Object entity) {
        List<ColumnData> columns = new ColumnExtractor(entity.getClass()).createColumnsWithValue(entity);

        return String.format(
                "insert into %s (%s) values (%s)",
                table.getName(),
                columnClause(columns),
                valueClause(columns)
        );
    }

    public String generateSelectQuery(BooleanBuilder booleanBuilder) {
        StringBuilder query = new StringBuilder();
        query.append("select ");
        query.append(selectClause());
        query.append(" from ");
        query.append(table.getName());

        if(booleanBuilder.isEmpty()) {
            return query.toString();
        }

        query.append(" where ");
        query.append(whereClause(booleanBuilder));

        return query.toString();
    }

    public String generateDeleteQuery(BooleanBuilder booleanBuilder) {
        StringBuilder query = new StringBuilder();
        query.append("delete from ");
        query.append(table.getName());

        if(booleanBuilder.isEmpty()) {
            return query.toString();
        }

        query.append(" where ");
        query.append(whereClause(booleanBuilder));

        return query.toString();
    }

    private String whereClause(BooleanBuilder booleanBuilder) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean firstLine = true;

        for (BooleanExpressionLine line : booleanBuilder.getExpressionLines()) {
            if (!firstLine) {
                stringBuilder.append(line.getLogicalOperator().name());
                stringBuilder.append(" ");
                firstLine = false;
            }
            BooleanExpression expression = line.getExpression();
            stringBuilder.append(expression.getColumn());
            stringBuilder.append(" ");
            stringBuilder.append(expression.getOperator().getSymbol());
            stringBuilder.append(" ");
            stringBuilder.append(valueToString(expression.getValue()));
        }

        return stringBuilder.toString();
    }

    private String selectClause() {
        return "*";
    }

    private String columnClause(List<ColumnData> columns) {
        if (columnClause != null) {
            return columnClause;
        }
        columnClause = columns.stream()
                // TODO: AUTO_INCREMENT 아닐때 대응하기
                .filter(column -> !column.isPrimaryKey())
                .map(ColumnData::getName)
                .collect(Collectors.joining(", "));

        return columnClause;
    }

    private String valueClause(List<ColumnData> columns) {
        return columns.stream()
                .filter(column -> !column.isPrimaryKey())
                .map(column -> valueToString(column.getValue()))
                .collect(Collectors.joining(", "));
    }

    private String valueToString(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return String.format("'%s'", value);
        }
        return value.toString();
    }
}
