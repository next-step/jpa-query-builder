package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class Constraint {
    private boolean isPrimaryKey;

    public Constraint(Field field) {
        this.isPrimaryKey = findIsPrimaryKey(field);
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    private boolean findIsPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }
}
