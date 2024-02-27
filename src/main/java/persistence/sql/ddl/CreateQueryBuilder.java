package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.domain.Column;
import persistence.sql.ddl.domain.Columns;
import persistence.sql.ddl.domain.Table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateQueryBuilder implements QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s (%s);";

    private final Columns columns;
    private final Table table;

    public CreateQueryBuilder(Class<?> clazz) {
        this.columns = new Columns(createColumns(clazz));
        this.table = new Table(clazz);
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
                CREATE_TABLE_QUERY,
                table.getName(),
                generateColumns()
        );
    }

    private String generateColumns() {
        return columns.getColumns().stream()
                .map(this::generateColumn)
                .collect(Collectors.joining(COMMA));
    }

    private String generateColumn(Column column) {
        return Stream.of(column.getName(),
                        DIALECT.getTypeString(column.getType(), column.getLength()),
                        generateConstraints(column))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(SPACE));
    }

    private String generateConstraints(Column column) {
        return column.getConstraints().stream()
                .map(DIALECT::getConstraintString)
                .collect(Collectors.joining(SPACE));
    }

}
