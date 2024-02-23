package persistence.sql.dml;

import persistence.sql.QueryBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelectQueryBuilder extends QueryBuilder {

    private static final String SELECT_QUERY = "SELECT %s FROM %s%s;";
    private static final String WHERE_CLAUSE = " WHERE %s";

    public String getSelectQueryString(Class<?> clazz, List<String> whereColumns, List<Object> whereValues) {
        return String.format(
                SELECT_QUERY,
                generateColumns(clazz.getDeclaredFields()),
                generateTableName(clazz),
                generateWhereClause(clazz, whereColumns, whereValues)
        );
    }

    @Override
    public String generateColumn(Field field) {
        return generateColumnName(field);
    }

    public Object generateWhereClause(Class<?> clazz, List<String> whereColumns, List<Object> whereValues) {
        if (whereColumns.size() != whereValues.size()) {
            throw new IllegalArgumentException("The number of columns and values corresponding to the condition statement do not match.");
        }

        String columnsAndValues = generateColumnsAndValues(clazz, whereColumns, whereValues);
        if (columnsAndValues.isEmpty()) {
            return EMPTY_STRING;
        }
        return String.format(WHERE_CLAUSE, columnsAndValues);
    }

    private String generateColumnsAndValues(Class<?> clazz, List<String> whereColumns, List<Object> whereValues) {
        return IntStream.range(0, whereColumns.size())
                .mapToObj(i -> {
                    try {
                        Field field = clazz.getDeclaredField(whereColumns.get(i));
                        String column = generateColumnName(field);
                        String value = String.valueOf(whereValues.get(i));

                        return column + " = " + convertValue(field.getType(), value);
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining(" AND "));
    }

}
