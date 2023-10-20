package persistence.sql.ddl;

import persistence.sql.common.ColumnUtils;

import static java.lang.String.format;

public class CreateQueryBuilder implements QueryBuilder{
    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE %s";

    private final QueryValidator queryValidator;

    private final Columns columns;

    private final Table table;

    public CreateQueryBuilder(QueryValidator queryValidator, Class<?> clazz) {
        this.queryValidator = queryValidator;
        this.columns = new Columns(ColumnUtils.convertClassToColumnList(clazz));
        this.table = new Table(clazz);
    }

    @Override
    public String buildQuery(Class<?> clazz) {
        queryValidator.checkIsEntity(clazz);

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
