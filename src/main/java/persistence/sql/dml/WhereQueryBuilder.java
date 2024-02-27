package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.domain.Column;
import persistence.sql.dml.domain.Value;
import persistence.sql.dml.domain.Values;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WhereQueryBuilder implements QueryBuilder {

    private static final String EMPTY_STRING = "";

    private static final String WHERE_CLAUSE = " WHERE %s";

    private final Values values;

    public WhereQueryBuilder(Class<?> clazz, List<String> whereColumns, List<Object> whereValues) {
        validate(whereColumns, whereValues);
        this.values = new Values(createValues(clazz, whereColumns, whereValues));
    }

    public WhereQueryBuilder(Value value) {
        this.values = new Values(List.of(value));
    }

    private void validate(List<String> whereColumns, List<Object> whereValues) {
        if (whereColumns.size() != whereValues.size()) {
            throw new IllegalArgumentException("The number of columns and values corresponding to the condition statement do not match.");
        }
    }

    private List<Value> createValues(Class<?> clazz, List<String> whereColumns, List<Object> whereValues) {
        return IntStream.range(0, whereColumns.size())
                .mapToObj(index -> createValue(clazz, whereColumns.get(index), whereValues.get(index)))
                .collect(Collectors.toList());
    }

    private Value createValue(Class<?> clazz, String column, Object value) {
        try {
            Field field = clazz.getDeclaredField(column);
            return new Value(new Column(field), field.getType(), value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String build() {
        String whereClause = generateWhereClause();
        if (whereClause.isEmpty()) {
            return EMPTY_STRING;
        }
        return String.format(WHERE_CLAUSE, whereClause);
    }

    private String generateWhereClause() {
        return values.getValues().stream()
                .map(x -> x.getColumn().getName() + " = " + x.getValue())
                .collect(Collectors.joining(" AND "));
    }

}
