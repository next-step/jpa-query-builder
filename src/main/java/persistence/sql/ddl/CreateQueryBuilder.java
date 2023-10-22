package persistence.sql.ddl;

import persistence.sql.QueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CreateQueryBuilder implements QueryBuilder {
    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE %s";

    private final QueryValidator queryValidator;

    private final Columns columns;

    private final Table table;

    public CreateQueryBuilder(QueryValidator queryValidator, Class<?> clazz) {
        this.queryValidator = queryValidator;
        queryValidator.checkIsEntity(clazz);
        this.columns = new Columns(convertClassToColumnList(clazz));
        this.table = new Table(clazz);
    }

    @Override
    public String buildQuery() {
        return format(CREATE_TABLE_COMMAND, table.getName()) +
                "(" +
                columns.buildColumnsToCreate() +
                ");";
    }

    private List<Column> convertClassToColumnList(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Column::new)
                .collect(Collectors.toList());
    }
}
