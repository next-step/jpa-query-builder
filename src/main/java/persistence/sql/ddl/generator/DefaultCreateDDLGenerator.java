package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityColumn;
import persistence.sql.ddl.EntityTable;
import persistence.sql.ddl.EntityIdColumn;
import persistence.sql.ddl.SqlJdbcTypes;
import persistence.sql.ddl.dialect.Dialect;

import java.sql.Types;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DefaultCreateDDLGenerator implements CreateDDLGenerator {
    private final Dialect dialect;

    public DefaultCreateDDLGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String generate(EntityTable entityTable) {
        String command = createCommand(entityTable);
        String definition = getDefinition(entityTable);

        return "%s (%s);".formatted(command, definition);
    }

    private String createCommand(EntityTable entityTable) {
        String name = entityTable.tableName();

        return "CREATE TABLE %s".formatted(name);
    }

    private String getDefinition(EntityTable entityTable) {
        String idDefinition = getIdColumnDefinition(entityTable.idColumn());
        Stream<String> columnDefinitions = entityTable.columns().stream().map(this::getColumnDefinition);

        return Stream.concat(Stream.of(idDefinition), columnDefinitions).collect(Collectors.joining(", "));
    }

    private String getIdColumnDefinition(EntityIdColumn idColumn) {
        Integer type = SqlJdbcTypes.typeOf(idColumn.type());

        String name = idColumn.name();
        String typeDefinition = dialect.getColumnDefinition(type, idColumn.entityColumn());
        String strategy = dialect.getGenerationDefinition(idColumn.generationType());

        return "%s %s %s".formatted(name, typeDefinition, strategy);
    }

    private String getColumnDefinition(EntityColumn column) {
        String name = column.name();
        String typeDefinition = getColumnTypeDefinition(column);
        String nullable = column.nullable() ? "" : "not null";

        return "%s %s %s".formatted(name, typeDefinition, nullable);
    }

    private String getColumnTypeDefinition(EntityColumn column) {
        Integer type = SqlJdbcTypes.typeOf(column.type());

        String typeDefinition = dialect.getColumnDefinition(type, column);

        if (type == Types.VARCHAR) {
            return typeDefinition.formatted(column.length());
        }

        return typeDefinition;
    }
}
