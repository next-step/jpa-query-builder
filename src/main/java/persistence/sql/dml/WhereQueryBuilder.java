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

    public WhereQueryBuilder(Class<?> clazz, List<String> whereColumns, List<Object> whereValues, List<String> whereOperators) {
        this.values = new Values(
                IntStream.range(0, whereColumns.size())
                        .mapToObj(index -> {
                            try {
                                Field field = clazz.getDeclaredField(whereColumns.get(index));
                                return new Value(new Column(field, TYPE_MAPPER, CONSTRAINT_MAPPER),
                                        field.getType(), whereValues.get(index));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList())
        );
    }

    @Override
    public String build() {
        String whereValues = values.getWhereValues();
        if (whereValues.isEmpty()) {
            return EMPTY_STRING;
        }
        return String.format(WHERE_CLAUSE, whereValues);
    }

}
