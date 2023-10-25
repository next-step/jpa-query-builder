package persistence.sql.ddl.utils;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.exception.InvalidIdColumnException;
import persistence.sql.ddl.type.DataType;

import java.lang.reflect.Field;

public class ColumnId implements ColumnType {

    private final ColumnType columnType;

    private final GenerationType generationType;


    public ColumnId(final Field field) {
        validateIdColumn(field);
        this.columnType = new ColumnField(field);
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
        return this.columnType.getName();
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
        return this.columnType.isTransient();
    }

    @Override
    public int getLength() {
        return this.columnType.getLength();
    }

    @Override
    public DataType getDataType() {
        return  this.columnType.getDataType();
    }

    public GenerationType getGenerationType() {
        return this.generationType;
    }
}
