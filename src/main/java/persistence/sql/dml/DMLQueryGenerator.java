package persistence.sql.dml;

import persistence.sql.mapping.*;

import java.util.List;
import java.util.stream.Collectors;

public class DMLQueryGenerator {
    private final TableData table;
    private final DataTypeMapper dataTypeMapper = new DataTypeMapper();

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
            stringBuilder.append(getValueString(expression.getValue()));
        }

        return stringBuilder.toString();
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
                .map(this::getValueString)
                .collect(Collectors.joining(", "));
    }

    public String getValueString(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return String.format("'%s'", value);
        }
        return value.toString();
    }
}
