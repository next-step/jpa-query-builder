package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.domain.Column;
import persistence.sql.ddl.domain.Columns;
import persistence.sql.ddl.domain.Table;
import persistence.sql.dml.domain.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder implements QueryBuilder {

    private static final String SELECT_QUERY = "SELECT %s FROM %s%s;";

    private final Table table;
    private final Columns columns;
    private final WhereQueryBuilder whereQueryBuilder;

    public SelectQueryBuilder(Class<?> clazz, List<String> whereColumns, List<Object> whereValues) {
        this.table = new Table(clazz);
        this.columns = new Columns(createColumns(clazz));
        this.whereQueryBuilder = new WhereQueryBuilder(clazz, whereColumns, whereValues);
    }

    public SelectQueryBuilder(Class<?> clazz, Object id) {
        this.table = new Table(clazz);
        this.columns = new Columns(createColumns(clazz));
        this.whereQueryBuilder = new WhereQueryBuilder(new Value(columns.getPrimaryKey(), String.valueOf(id)));
    }

    private List<Column> createColumns(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransientAnnotationPresent)
                .map(Column::new)
                .collect(Collectors.toList());
    }

    @Override
    public String build() {
        return String.format(
                SELECT_QUERY,
                generateColumns(),
                table.getName(),
                whereQueryBuilder.build()
        );
    }

    private String generateColumns() {
        return columns.getColumns().stream()
                .map(Column::getName)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(COMMA));
    }
}
