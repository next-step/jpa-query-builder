package persistence.sql.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PKColumn implements BaseColumn {

    private final Column column;

    private final GenerationType generationType;

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

    private GenerationType buildGenerationType(Field field) {
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);

        if (generatedValue == null) {
            return null;
        }

        return generatedValue.strategy();
    }

    @Override
    public Field getField() {
        return column.getField();
    }

    @Override
    public String getName() {
        return column.getName();
    }

    @Override
    public SqlType getType() {
        return column.getType();
    }

    @Override
    public List<SqlConstraint> getConstraints() {
        return column.getConstraints();
    }

    public Optional<GenerationType> getGenerationType() {
        return Optional.ofNullable(generationType);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PKColumn pkColumn = (PKColumn) object;

        if (!Objects.equals(column, pkColumn.column)) return false;
        return generationType == pkColumn.generationType;
    }

    @Override
    public int hashCode() {
        int result = column != null ? column.hashCode() : 0;
        result = 31 * result + (generationType != null ? generationType.hashCode() : 0);
        return result;
    }
}
