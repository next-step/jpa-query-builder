package persistence.sql.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class PKColumn {

    private final Column column;

    private final Optional<GenerationType> generationType;

    public PKColumn(Field pkField) {
        validatePKField(pkField);

        this.column = buildColumn(pkField);
        this.generationType = buildGenerationType(pkField);
    }

    private void validatePKField(Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            throw new IllegalArgumentException("This field is not a pk: " + field.getName());
        }
    }

    private Column buildColumn(Field field) {
        return new Column(field);
    }

    private Optional<GenerationType> buildGenerationType(Field field) {
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);

        if (generatedValue != null) {
            GenerationType strategy = generatedValue.strategy();
            return Optional.of(strategy);
        }

        return Optional.empty();
    }

    public String getName() {
        return column.getName();
    }

    public SqlType getType() {
        return column.getType();
    }

    public List<SqlConstraint> getConstraints() {
        return column.getConstraints();
    }

    public Optional<GenerationType> getGenerationType() {
        return generationType;
    }

    public Object getValue(Object instance) {
        return column.getValue(instance);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PKColumn pkColumn = (PKColumn) object;

        if (!column.equals(pkColumn.column)) return false;
        return generationType.equals(pkColumn.generationType);
    }

    @Override
    public int hashCode() {
        int result = column.hashCode();
        result = 31 * result + generationType.hashCode();
        return result;
    }
}
