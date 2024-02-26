package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.domain.Column;
import persistence.sql.ddl.domain.Table;
import persistence.sql.dml.domain.Value;
import persistence.sql.dml.domain.Values;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteQueryBuilder implements QueryBuilder {

    private static final String DELETE_QUERY = "DELETE FROM %s%s;";

    private final Table table;
    private final Values values;
    private final WhereQueryBuilder whereQueryBuilder;

    public DeleteQueryBuilder(Class<?> clazz, List<String> whereColumns, List<Object> whereValues) {
        this.table = new Table(clazz);
        this.values = new Values(List.of());
        this.whereQueryBuilder = new WhereQueryBuilder(clazz, whereColumns, whereValues);
    }

    public DeleteQueryBuilder(Object object) {
        Class<?> clazz = object.getClass();
        this.table = new Table(clazz);
        this.values = new Values(createValues(object, clazz));
        this.whereQueryBuilder = new WhereQueryBuilder(values.getPrimaryKeyValue());
    }

    private List<Value> createValues(Object object, Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransientAnnotationPresent)
                .map(field -> new Value(new Column(field), field, object))
                .collect(Collectors.toList());
    }

    @Override
    public String build() {
        return String.format(
                DELETE_QUERY,
                table.getName(),
                whereQueryBuilder.build()
        );
    }
}
