package persistence.sql.mapper;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.exception.InvalidIdColumnException;
import persistence.sql.ddl.type.DataType;

import java.lang.reflect.Field;

public class ColumnId implements ColumnType {

    private final ColumnType delegate;

    private final GenerationType generationType;


    public ColumnId(final Object entity, final Field field) {
        validateIdColumn(field);
        this.delegate = new ColumnField(entity, field);
        this.generationType = paresGenerationType(field);
    }

    private GenerationType paresGenerationType(final Field field) {
        GeneratedValue generateValue = field.getAnnotation(GeneratedValue.class);
        return generateValue.strategy();
    }

    private void validateIdColumn(final Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            throw new InvalidIdColumnException();
        }
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public Object getValue() {
        return this.delegate.getValue();
    }

    @Override
    public boolean isId() {
        return true;
    }

    @Override
    public boolean isNullable() {
        return false;
    }

    @Override
    public boolean isTransient() {
        return this.delegate.isTransient();
    }

    @Override
    public String getLength() {
        return this.delegate.getLength();
    }

    @Override
    public DataType getDataType() {
        return this.delegate.getDataType();
    }

    public GenerationType getGenerationType() {
        return this.generationType;
    }
}
