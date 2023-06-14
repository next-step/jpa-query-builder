package persistence.sql.ddl.mapping;

import jakarta.persistence.Id;
import persistence.sql.ddl.exception.NoIdentifierException;

import java.lang.reflect.Field;

public class PrimaryKey {

    private final Field field;

    public PrimaryKey(Field field) {
        validate(field);
        this.field = field;
    }

    private void validate(Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            throw new NoIdentifierException(field.getDeclaringClass().getName());
        }
    }

    public String createDdlString() {
        return String.format(", primary key (%s)", field.getName());
    }
}
