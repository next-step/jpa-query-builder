package persistence.sql.ddl.utils;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.exception.InvalidIdColumnException;

import java.lang.reflect.Field;

public class ColumnId implements ColumnType2 {

    private final ColumnType2 columnType2;

    private final GenerationType generationType;


    public ColumnId(final Field field) {
        validateIdColumn(field);
        this.columnType2 = new ColumnField(field);
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
        return this.columnType2.getName();
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
        return this.columnType2.isTransient();
    }

    @Override
    public int getLength() {
        return this.columnType2.getLength();
    }

    public GenerationType getGenerationType() {
        return this.generationType;
    }
}
