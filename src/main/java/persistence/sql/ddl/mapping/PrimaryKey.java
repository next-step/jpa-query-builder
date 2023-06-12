package persistence.sql.ddl.mapping;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class PrimaryKey {

    private final Field field;

    public PrimaryKey(Field field) {
        validate(field);
        this.field = field;
    }

    private void validate(Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            throw new IllegalArgumentException();
        }
    }

    public String createDdlString() {
        return String.format(", primary key (%s)", field.getName());
    }
}
