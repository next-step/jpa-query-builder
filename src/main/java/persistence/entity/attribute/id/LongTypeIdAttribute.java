package persistence.entity.attribute.id;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.converter.SqlConverter;

import java.lang.reflect.Field;
import java.util.Optional;

public class LongTypeIdAttribute extends IdAttribute {
    private final String fieldName;
    private final String columnName;
    private final GenerationType generationType;

    private LongTypeIdAttribute(
            String fieldName,
            String columnName,
            GenerationType generationType
    ) {
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.generationType = generationType;
    }

    public static LongTypeIdAttribute of(Field field) {
        return new LongTypeIdAttribute(
                field.getName(),
                Optional.ofNullable(field.getAnnotation(Column.class)).map(Column::name).orElse(field.getName()),
                Optional.ofNullable(field.getAnnotation(GeneratedValue.class)).map(GeneratedValue::strategy).orElse(null)
        );
    }

    @Override
    public String prepareDDL(SqlConverter sqlConverter) {
        String component = (columnName.isBlank() ? fieldName : columnName) + " " +
                sqlConverter.convert(Long.class) + " " +
                sqlConverter.convert(generationType.getClass());
        return component.trim();
    }

    @Override
    public String getColumnName() {
        return this.columnName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public GenerationType getGenerationType() {
        return this.generationType;
    }
}
