package persistence.sql.domain;

import java.lang.reflect.Field;
import java.util.Optional;

public class FieldColumn implements Column {
    private final String name;
    private final DataType type;
    private final Field field;
    private final boolean isNullable;

    public FieldColumn(String name, DataType type, Field field, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.field = field;
        this.isNullable = isNullable;
    }

    public static FieldColumn from(Field target) {
        String name = getName(target);
        DataType dataType = DataType.from(target.getType());
        Boolean isNullable = getNullable(target);
        return new FieldColumn(name, dataType, target, isNullable);
    }

    private static String getName(Field target) {
        return Optional.ofNullable(target.getAnnotation(jakarta.persistence.Column.class))
                .map(jakarta.persistence.Column::name)
                .filter(name -> !name.isBlank())
                .orElse(target.getName());
    }

    private static Boolean getNullable(Field target) {
        return target.isAnnotationPresent(jakarta.persistence.Column.class) &&
                target.getAnnotation(jakarta.persistence.Column.class).nullable();
    }

    @Override
    public DataType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public boolean isId() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return isNullable;
    }

    @Override
    public boolean isAutoIncrementId() {
        return false;
    }
}
