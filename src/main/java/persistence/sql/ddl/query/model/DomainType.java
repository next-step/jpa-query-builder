package persistence.sql.ddl.query.model;

import java.lang.reflect.Field;

public class DomainType {

    private final String name;
    private final Class<?> classType;
    private final Field field;

    public DomainType(final String name,
                      final Class<?> classType,
                      final Field field) {
        this.name = name;
        this.classType = classType;
        this.field = field;
    }

    public static DomainType from(Field field) {
        return new DomainType(
                field.getName(),
                field.getType(),
                field
        );
    }

    public String getName() {
        return name;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public Field getField() {
        return field;
    }
}
