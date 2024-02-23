package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.domain.Column;
import persistence.sql.ddl.domain.Columns;
import persistence.sql.ddl.domain.Table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder implements QueryBuilder {

    private static final String SELECT_QUERY = "SELECT %s FROM %s%s;";

    private final Table table;
    private final Columns columns;
    private final WhereQueryBuilder whereQueryBuilder;

    public SelectQueryBuilder(Class<?> clazz, List<String> whereColumns, List<Object> whereValues, List<String> whereOperators) {
        if (whereColumns.size() != whereValues.size()) {
            throw new IllegalArgumentException("The number of columns and values corresponding to the condition statement do not match.");
        }

        this.table = new Table(clazz);
        this.columns = new Columns(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> new Column(field, TYPE_MAPPER, CONSTRAINT_MAPPER)).collect(Collectors.toList()));
        this.whereQueryBuilder = new WhereQueryBuilder(clazz, whereColumns, whereValues, whereOperators);
    }

    public String build() {
        return String.format(
                SELECT_QUERY,
                columns.getSelectColumns(),
                table.getName(),
                whereQueryBuilder.build()
        );
    }
}
