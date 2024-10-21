package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityColumn;
import persistence.sql.ddl.Table;
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
    public String generate(Table table) {
        String command = createCommand(table);
        String definition = getDefinition(table);

        return "%s (%s);".formatted(command, definition);
    }

    private String createCommand(Table table) {
        String name = table.tableName();

        return "CREATE TABLE %s".formatted(name);
    }

    private String getDefinition(Table table) {
        String idDefinition = getIdFieldDefinition(table.idField());
        Stream<String> fieldDefinitions = table.fields().stream().map(this::getFieldDefinition);

        return Stream.concat(Stream.of(idDefinition), fieldDefinitions).collect(Collectors.joining(", "));
    }

    private String getIdFieldDefinition(EntityIdColumn idField) {
        Integer type = SqlJdbcTypes.typeOf(idField.type());

        String name = idField.name();
        String typeDefinition = dialect.getFieldDefinition(type);
        String strategy = dialect.getGenerationDefinition(idField.generationType());

        return "%s %s %s".formatted(name, typeDefinition, strategy);
    }

    private String getFieldDefinition(EntityColumn field) {
        String name = field.name();
        String typeDefinition = getFieldTypeDefinition(field);
        String nullable = field.nullable() ? "" : "not null";

        return "%s %s %s".formatted(name, typeDefinition, nullable);
    }

    private String getFieldTypeDefinition(EntityColumn field) {
        Integer type = SqlJdbcTypes.typeOf(field.type());

        String typeDefinition = dialect.getFieldDefinition(type);

        if (type == Types.VARCHAR) {
            return typeDefinition.formatted(field.length());
        }

        return typeDefinition;
    }
}
