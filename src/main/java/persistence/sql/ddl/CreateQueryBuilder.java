package persistence.sql.ddl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CreateQueryBuilder extends QueryBuilder{
    private final static String CREATE_TABLE_COMMAND = "CREATE TABLE %s";

    public CreateQueryBuilder() {
    }

    public String buildQuery(Class<?> clazz) {
        checkIsEntity(clazz);

        return format(CREATE_TABLE_COMMAND, findTableName(clazz)) +
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
