package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class H2TypeMapper implements TypeMapper {

    private static final String EMPTY_STRING = "";

    @Override
    public String getType(Field field) {
        return String.join(EMPTY_STRING,
                H2DataType.of(field.getType()).name(),
                getTypeLength(field)
        );
    }

    private String getTypeLength(Field field) {
        if (H2DataType.of(field.getType()).getDefaultLength() != null) {
            return String.format("(%d)", H2DataType.of(field.getType()).getDefaultLength());
        }

        if (field.isAnnotationPresent(Column.class)) {
            return String.format("(%d)", field.getAnnotation(Column.class).length());
        }
        return EMPTY_STRING;
    }
}
