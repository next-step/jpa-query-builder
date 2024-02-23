package persistence.sql.ddl.mapper;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class H2TypeMapper implements TypeMapper {

    @Override
    public String getType(Field field) {
        return String.join(EMPTY_STRING,
                H2DataType.of(field.getType()).name(),
                getTypeLength(field)
        );
    }

    private String getTypeLength(Field field) {
        if (H2DataType.of(field.getType()).getDefaultLength() == null) {
            return EMPTY_STRING;
        }

        if (!field.isAnnotationPresent(Column.class)) {
            return "(" + H2DataType.of(field.getType()).getDefaultLength() + ")";
        }

        return "(" + field.getAnnotation(Column.class).length() + ")";
    }
}
