package persistence.sql.ddl;

import persistence.sql.column.*;
import persistence.sql.dialect.Database;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder implements QueryBuilder {

    private static final String CREATE_TABLE_DDL = "create table %s (%s)";
    private static final String COMMA = ", ";

    private final TableColumn tableColumn;
    private final List<Column> columns;

    private CreateQueryBuilder(TableColumn tableColumn, List<Column> columns) {
        this.tableColumn = tableColumn;
        this.columns = columns;
    }

    public static CreateQueryBuilder generate(Class<?> clazz, Database database) {
        ColumnGenerator columnGenerator = new ColumnGenerator(new GeneralColumnFactory());
        TableColumn tableColumn = TableColumn.from(clazz);
        List<Column> columns = columnGenerator.of(clazz.getDeclaredFields(), database.createDialect());
        return new CreateQueryBuilder(tableColumn, columns);
    }

    @Override
    public String build() {
        String columnQuery = this.columns
                .stream()
                .map(Column::getDefinition)
                .collect(Collectors.joining(COMMA));

        return String.format(CREATE_TABLE_DDL, tableColumn.getName(), columnQuery);
    }
}
