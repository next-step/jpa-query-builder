package persistence.sql.ddl;

import java.util.stream.Collectors;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.processor.FieldBuilder;
import persistence.sql.ddl.wrapper.Table;

public class CreateQueryBuilder implements QueryBuilder {

    private static CreateQueryBuilder instance = null;

    private CreateQueryBuilder() {
    }

    public static synchronized CreateQueryBuilder getInstance() {
        if (instance == null) {
            instance = new CreateQueryBuilder();
        }
        return instance;
    }

    private final FieldBuilder fieldBuilder = FieldBuilder.getInstance();

    private static final String CREATE_TABLE_DEFINITION = "CREATE TABLE %s (%s)";


    @Override
    public String builder(Class<?> clazz) {

        Table table = Table.of(clazz);

        String columnDefinitions = table.getColumns().stream()
            .map(fieldBuilder::process)
            .collect(Collectors.joining(","));

        return String.format(CREATE_TABLE_DEFINITION, table.getTableName(), columnDefinitions);
    }
}
