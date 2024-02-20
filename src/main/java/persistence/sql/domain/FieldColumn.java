package persistence.sql.domain;

import java.lang.reflect.Field;
import java.util.Optional;

public class FieldColumn implements Column {
    private final String name;
    private final DataType type;
    private final Field field;

    public FieldColumn(String name, DataType type, Field field) {
        this.name = name;
        this.type = type;
        this.field = field;
    }

    public static FieldColumn from(Field target) {
        String name = getName(target);
        DataType dataType = DataType.from(target.getType());
        return new FieldColumn(name, dataType, target);
    }

    private static String getName(Field target) {
        return Optional.ofNullable(target.getAnnotation(jakarta.persistence.Column.class))
                .map(jakarta.persistence.Column::name)
                .filter(name -> !name.isBlank())
                .orElse(target.getName());
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
}
