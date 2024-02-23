package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.domain.Column;
import persistence.sql.ddl.domain.Columns;
import persistence.sql.ddl.domain.Table;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateQueryBuilder implements QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s (%s);";

    private final Columns columns;
    private final Table table;

    public CreateQueryBuilder(Class<?> clazz) {
        this.columns = new Columns(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> new Column(field, TYPE_MAPPER, CONSTRAINT_MAPPER)).collect(Collectors.toList()));
        this.table = new Table(clazz);
    }

    @Override
    public String build() {
        return String.format(
                CREATE_TABLE_QUERY,
                table.getName(),
                columns.getDDLColumns());
    }

}
