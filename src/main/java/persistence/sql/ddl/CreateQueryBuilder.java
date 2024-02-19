package persistence.sql.ddl;

import java.util.stream.Collectors;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.wrapper.Table;
import persistence.sql.dialect.Dialect;

public class CreateQueryBuilder implements QueryBuilder {

    private final FieldQueryGenerator fieldBuilder;
    private static final String CREATE_TABLE_DEFINITION = "CREATE TABLE %s (%s)";
    private static final String COMMA = ",";

    private CreateQueryBuilder(Dialect dialect) {
        fieldBuilder = FieldQueryGenerator.from(dialect);
    }

    public static CreateQueryBuilder from(Dialect dialect) {
        return new CreateQueryBuilder(dialect);
    }

    @Override
    public String generateQuery(Class<?> clazz) {

        Table table = Table.of(clazz);

        String columnDefinitions = table.getColumns().stream()
            .map(fieldBuilder::generate)
            .collect(Collectors.joining(COMMA));

        return String.format(CREATE_TABLE_DEFINITION, table.getTableName(), columnDefinitions);
    }
}
