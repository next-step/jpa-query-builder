package persistence.sql.ddl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CreateQueryBuilder implements QueryBuilder{
    private final static String CREATE_TABLE_COMMAND = "CREATE TABLE %s";

    private QueryValidator queryValidator;

    public CreateQueryBuilder(QueryValidator queryValidator) {
        this.queryValidator = queryValidator;
    }

    @Override
    public String buildQuery(Class<?> clazz) {
        queryValidator.checkIsEntity(clazz);

        return format(CREATE_TABLE_COMMAND, new Table(clazz).getName()) +
                "(" +
                new ColumnBuilder((convertClassToColumnList(clazz))).buildColumnList() +
                ");";
    }

    private List<Column> convertClassToColumnList(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Column::new)
                .collect(Collectors.toList());
    }
}
