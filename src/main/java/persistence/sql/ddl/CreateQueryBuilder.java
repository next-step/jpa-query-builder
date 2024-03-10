package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.metadata.ColumnMetadata;
import persistence.sql.metadata.EntityMetadata;

import java.util.stream.Collectors;

public class CreateQueryBuilder {

    public static final String COLUMN_DEFINITION_DELIMITER = " ";
    public static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s (%s)";
    public static final String PRIMARY_KEY_TEMPLATE = "PRIMARY KEY (%s)";
    public static final String DELIMITER = ", ";
    private final Dialect dialect;
    private final EntityMetadata entity;

    private CreateQueryBuilder(Dialect dialect, EntityMetadata entity) {
        this.dialect = dialect;
        this.entity = entity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Dialect dialect;
        private EntityMetadata entity;

        private Builder() {
        }

        public Builder dialect(Dialect dialect) {
            this.dialect = dialect;
            return this;
        }

        public Builder entity(Class<?> clazz) {
            this.entity = EntityMetadata.of(clazz);
            return this;
        }

        public CreateQueryBuilder build() {
            return new CreateQueryBuilder(dialect, entity);
        }
    }

    private String generateColumnsQuery() {
        return entity.getColumns().getColumns().stream()
                .map(this::generateColumnQuery)
                .collect(Collectors.joining(DELIMITER));
    }

    private String generateColumnQuery(ColumnMetadata column) {
        return String.join(COLUMN_DEFINITION_DELIMITER, column.getName(), dialect.build(column));
    }

    public String generateQuery() {
        return String.format(CREATE_TABLE_TEMPLATE,
                entity.getName(),
                String.join(DELIMITER,
                        generateColumnsQuery(),
                        String.format(PRIMARY_KEY_TEMPLATE, entity.getPrimaryKey().getName()))
        );
    }
}
