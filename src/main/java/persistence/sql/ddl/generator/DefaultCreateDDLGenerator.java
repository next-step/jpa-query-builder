package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityField;
import persistence.sql.ddl.EntityFields;
import persistence.sql.ddl.EntityIdField;
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
    public String generate(EntityFields entityFields) {
        String command = createCommand(entityFields);
        String definition = getDefinition(entityFields);

        return "%s (%s);".formatted(command, definition);
    }

    private String createCommand(EntityFields entityFields) {
        String name = entityFields.tableName();

        return "CREATE TABLE %s".formatted(name);
    }

    private String getDefinition(EntityFields entityFields) {
        String idDefinition = getIdFieldDefinition(entityFields.idField());
        Stream<String> fieldDefinitions = entityFields.fields().stream().map(this::getFieldDefinition);

        return Stream.concat(Stream.of(idDefinition), fieldDefinitions).collect(Collectors.joining(", "));
    }

    private String getIdFieldDefinition(EntityIdField idField) {
        Integer type = SqlJdbcTypes.typeOf(idField.type());

        String name = idField.name();
        String typeDefinition = dialect.getFieldDefinition(type);
        String strategy = dialect.getGenerationDefinition(idField.generationType());

        return "%s %s %s".formatted(name, typeDefinition, strategy);
    }

    private String getFieldDefinition(EntityField field) {
        String name = field.name();
        String typeDefinition = getFieldTypeDefinition(field);
        String nullable = field.nullable() ? "" : "not null";

        return "%s %s %s".formatted(name, typeDefinition, nullable);
    }

    private String getFieldTypeDefinition(EntityField field) {
        Integer type = SqlJdbcTypes.typeOf(field.type());

        String typeDefinition = dialect.getFieldDefinition(type);

        if (type == Types.VARCHAR) {
            return typeDefinition.formatted(field.length());
        }

        return typeDefinition;
    }
}
