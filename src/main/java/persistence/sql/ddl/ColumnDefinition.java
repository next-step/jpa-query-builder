package persistence.sql.ddl;

public record ColumnDefinition(
        String name,
        String type,
        String identity,
        String nullable,
        String primaryKey
) {
}
