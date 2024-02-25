package persistence.sql.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class IdColumn implements Column {
    private final GenerationType generationType;
    private final FieldColumn fieldColumn;

    public IdColumn(GenerationType generationType, FieldColumn fieldColumn) {
        this.generationType = generationType;
        this.fieldColumn = fieldColumn;
    }

    public static IdColumn from(Field target) {
        GenerationType generationType = getGenerationType(target);
        FieldColumn fieldColumn = FieldColumn.from(target);
        return new IdColumn(generationType, fieldColumn);
    }

    private static GenerationType getGenerationType(Field target) {
        if (!target.isAnnotationPresent(GeneratedValue.class)) {
            return GenerationType.AUTO;
        }
        return target.getAnnotation(GeneratedValue.class).strategy();
    }

    @Override
    public boolean isId() {
        return true;
    }

    @Override
    public boolean isNullable() {
        return fieldColumn.isNullable();
    }

    @Override
    public DataType getType() {
        return fieldColumn.getType();
    }

    @Override
    public String getName() {
        return fieldColumn.getName();
    }

    @Override
    public Field getField() {
        return fieldColumn.getField();
    }

    @Override
    public boolean isAutoIncrementId() {
        return GenerationType.IDENTITY.equals(generationType);
    }
}
