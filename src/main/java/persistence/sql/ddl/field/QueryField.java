package persistence.sql.ddl.field;

import persistence.sql.ddl.type.DatabaseSchema;
import persistence.sql.ddl.type.DatabaseSchemaType;

import java.lang.reflect.Field;

public class QueryField {

    public static final String DELIMITER = " ";
    private final Field field;
    private final DatabaseSchema schema;

    public QueryField(Field field) {
        this.field = field;
        this.schema = DatabaseSchemaType.from(field);
    }

    public String toSQL() {
        return String.join(DELIMITER,
                schema.getName(field),
                schema.getType(field),
                schema.getConstraints(field)
        ).trim();
    }
}
