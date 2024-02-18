package persistence.sql.ddl.type.impl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.util.Optional;
import persistence.sql.ddl.type.DataTypeBuilder;
import persistence.sql.ddl.type.SqlDataType;

public class DefaultDataTypeBuilder implements DataTypeBuilder {

    @Override
    public String getDataTypeDefinitionFrom(Field field) {
        SqlDataType sqlDataType = SqlDataType.of(field);

        return getDataLengthFrom(field)
            .map(length -> sqlDataType + "(" + length + ")")
            .orElse(sqlDataType.toString());
    }

    public Optional<Integer> getDataLengthFrom(Field field) {
        if (field.getType() != String.class) {
            return Optional.empty();
        }

        if (field.isAnnotationPresent(Column.class)) {
            return Optional.of(field.getAnnotation(Column.class).length());
        }

        return Optional.of(255);

    }
}
