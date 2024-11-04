package persistence.sql.ddl;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;
import persistence.sql.metadata.Column;
import persistence.sql.metadata.EntityMetadata;

import java.util.stream.Collectors;

public class DdlQueryBuilder {

    private static final String JOIN_DELIMITER = ", ";

    private final Dialect dialect;

    public DdlQueryBuilder() {
        this(new H2Dialect());
    }

    public DdlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String buildCreateQuery(Class<?> clazz) {
        EntityMetadata entityMetadata = EntityMetadata.from(clazz);
        return "create table %s (%s);".formatted(entityMetadata.getTableName(), getDefinitions(entityMetadata));
    }

    public String buildDropQuery(Class<?> clazz) {
        EntityMetadata entityMetadata = EntityMetadata.from(clazz);
        return "drop table %s;".formatted(entityMetadata.getTableName());
    }

    private String getDefinitions(EntityMetadata entityMetadata) {
        return "%s, primary key (%s)".formatted(generateColumnDefinitions(entityMetadata), generatePrimaryKeyNames(entityMetadata));
    }

    private String generatePrimaryKeyNames(EntityMetadata entityMetadata) {
        return String.join(JOIN_DELIMITER, entityMetadata.getPrimaryKeyName());
    }

    private String generateColumnDefinitions(EntityMetadata entityMetadata) {
        return entityMetadata.getColumns().stream()
                .map(this::generateColumnDefinition)
                .collect(Collectors.joining(JOIN_DELIMITER));
    }

    private String generateColumnDefinition(Column column) {
        if (column.hasOptions()) {
            return "%s %s %s".formatted(column.getName(), column.getSqlType(dialect), String.join(" ", column.getSqlOptions(dialect)));
        }

        return "%s %s".formatted(column.getName(), column.getSqlType(dialect));
    }
}
