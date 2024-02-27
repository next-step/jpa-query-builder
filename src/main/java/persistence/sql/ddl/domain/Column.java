package persistence.sql.ddl.domain;

import jakarta.persistence.Id;
import persistence.sql.ddl.constraint.Constraint;
import persistence.sql.ddl.constraint.NotNull;
import persistence.sql.ddl.constraint.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Column {

    private final Field field;

    private final String name;

    private final Type type;

    private final int length;

    private final Constraints constraints;

    public Column(Field field) {
        this.field = field;
        this.name = createColumnName(field);
        this.type = createColumnType(field);
        this.length = createColumnLength(field);
        this.constraints = createColumnConstraints(field);
    }

    private Constraints createColumnConstraints(Field field) {
        final List<Constraint> newConstraints = new ArrayList<>();
        if (field.isAnnotationPresent(jakarta.persistence.Column.class) && !field.getAnnotation(jakarta.persistence.Column.class).nullable()) {
            newConstraints.add(new NotNull());
        }

        if (field.isAnnotationPresent(Id.class)) {
            newConstraints.add(new PrimaryKey(field));
        }

        return new Constraints(newConstraints);
    }

    private int createColumnLength(Field field) {
        if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
            return field.getAnnotation(jakarta.persistence.Column.class).length();
        }
        return 0;
    }

    public String createColumnName(Field field) {
        if (field.isAnnotationPresent(jakarta.persistence.Column.class) && !field.getAnnotation(jakarta.persistence.Column.class).name().isBlank()) {
            return field.getAnnotation(jakarta.persistence.Column.class).name();
        }

        return field.getName();
    }

    public Type createColumnType(Field field) {
        return Type.of(field.getType());
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Field getField() {
        return field;
    }

    public List<Constraint> getConstraints() {
        return constraints.getConstraints();
    }

    public boolean isPrimaryKey() {
        return constraints.hasPrimaryKeyConstraint();
    }
}
