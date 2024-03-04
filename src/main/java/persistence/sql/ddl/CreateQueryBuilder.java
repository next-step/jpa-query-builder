package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.metadata.Column;
import persistence.sql.metadata.Columns;
import persistence.sql.metadata.Entity;
import persistence.sql.metadata.PrimaryKey;

import java.util.stream.Collectors;

public class CreateQueryBuilder extends QueryBuilder {

    public static final String COLUMN_DEFINITION_DELIMITER = " ";
    public static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s (%s)";
    public static final String PRIMARY_KEY_TEMPLATE = "PRIMARY KEY (%s)";
    public static final String DELIMITER = ", ";
    private final Entity entity;
    private final Columns columns;
    private final PrimaryKey primaryKey;

    private CreateQueryBuilder(Entity entity, Columns columns, PrimaryKey primaryKey) {
        this.entity = entity;
        this.columns = columns;
        this.primaryKey = primaryKey;
    }

    public static CreateQueryBuilder of(Class<?> clazz) {
        return new CreateQueryBuilder(Entity.of(clazz), Columns.of(createColumns(clazz, null)), PrimaryKey.of(generatePrimaryKey(clazz)));
    }

    private String generateColumnsQuery() {
        return columns.getColumns().stream()
                .map(this::generateColumnQuery)
                .collect(Collectors.joining(DELIMITER));
    }

    private String generateColumnQuery(Column column) {
        return String.join(COLUMN_DEFINITION_DELIMITER, column.getName(), DIALECT.build(column));
    }

    @Override
    public String build() {
        return String.format(CREATE_TABLE_TEMPLATE,
                entity.getName(),
                String.join(DELIMITER,
                        generateColumnsQuery(),
                        String.format(PRIMARY_KEY_TEMPLATE, primaryKey.getName()))
        );
    }
}
