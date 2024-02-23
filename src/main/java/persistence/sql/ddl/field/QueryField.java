package persistence.sql.ddl.field;

import java.lang.reflect.Field;

public class QueryField {

    public static final String DELIMITER = " ";
    private final Field field;
    private final DatabaseSchemaType schemaType;

    public QueryField(Field field) {
        this.field = field;
        this.schemaType = DatabaseSchemaType.from(field);
    }

    public String toSQL() {
        return String.join(DELIMITER,
                schemaType.getName(field),
                schemaType.getType(field),
                schemaType.getConstraints(field)
        ).trim();
    }
}
