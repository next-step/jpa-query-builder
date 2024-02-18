package persistence.sql.ddl;

import java.util.stream.Collectors;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.wrapper.Table;
import persistence.sql.dialet.Dialect;

public class CreateQueryBuilder implements QueryBuilder {

    private static CreateQueryBuilder instance = null;

    private final FieldBuilder fieldBuilder;
    private static final String CREATE_TABLE_DEFINITION = "CREATE TABLE %s (%s)";
    private static final String COMMA = ",";

    private CreateQueryBuilder(Dialect dialect) {
        fieldBuilder = FieldBuilder.getInstance(dialect);
    }

    public static synchronized CreateQueryBuilder getInstance(Dialect dialect) {
        if (instance == null) {
            instance = new CreateQueryBuilder(dialect);
        }
        return instance;
    }

    @Override
    public String builder(Class<?> clazz) {

        Table table = Table.of(clazz);

        String columnDefinitions = table.getColumns().stream()
            .map(fieldBuilder::builder)
            .collect(Collectors.joining(COMMA));

        return String.format(CREATE_TABLE_DEFINITION, table.getTableName(), columnDefinitions);
    }
}
