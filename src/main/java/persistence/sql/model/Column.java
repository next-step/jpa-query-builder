package persistence.sql.model;

import jakarta.persistence.Id;
import util.CaseConverter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Column {

    private final String name;

    private final SqlType type;

    private final List<SqlConstraint> constraints;

    public Column(Field field) {
        this.name = getName(field);
        this.type = getType(field);
        this.constraints = getConstraints(field);
    }

    private String getName(Field field) {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);

        if (column != null && hasName(column)) {
            return column.name();
        }

        String name = field.getName();
        return CaseConverter.camelToSnake(name);
    }

    private boolean hasName(jakarta.persistence.Column column) {
        String name = column.name();
        return !name.isEmpty();
    }

    private SqlType getType(Field field) {
        Class<?> type = field.getType();
        return SqlType.of(type);
    }

    private List<SqlConstraint> getConstraints(Field field) {

        List<SqlConstraint> constraints = new ArrayList<>();

        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);
        if (column != null) {
            constraints.addAll(SqlConstraint.of(column));
        }

        Id id = field.getDeclaredAnnotation(Id.class);

        if (id != null) {
            constraints.addAll(SqlConstraint.of(id));
        }

        return constraints;
    }

    public String getName() {
        return name;
    }

    public SqlType getType() {
        return type;
    }

    public List<SqlConstraint> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Column column = (Column) object;

        if (!name.equals(column.name)) return false;
        if (type != column.type) return false;
        return constraints.equals(column.constraints);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + constraints.hashCode();
        return result;
    }
}
