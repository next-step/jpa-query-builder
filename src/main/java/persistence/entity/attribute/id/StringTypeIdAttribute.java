package persistence.entity.attribute.id;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.converter.SqlConverter;

import java.lang.reflect.Field;
import java.util.Optional;

public class StringTypeIdAttribute extends IdAttribute {
    private final String fieldName;
    private final Integer length;
    private final String columnName;
    private final GenerationType generateValueStrategy;

    public StringTypeIdAttribute(
            String fieldName,
            Integer length,
            String columnName,
            GenerationType generateValueStrategy
    ) {
        this.fieldName = fieldName;
        this.length = length;
        this.columnName = columnName;
        this.generateValueStrategy = generateValueStrategy;
    }

    public static StringTypeIdAttribute of(Field field) {

        Optional<Column> columnOptional = Optional.ofNullable(field.getAnnotation(Column.class));

        return new StringTypeIdAttribute(
                field.getName(),
                columnOptional.map(Column::length).orElse(255),
                columnOptional.map(Column::name).orElse(field.getName()),
                null
        );
    }

    @Override
    public String prepareDDL(SqlConverter sqlConverter) {
        String component = (columnName.isBlank() ? fieldName : columnName) +
                " " + sqlConverter.convert(String.class) +
                String.format("(%s)", length) +
                " ";
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
        return this.generateValueStrategy;
    }
}
