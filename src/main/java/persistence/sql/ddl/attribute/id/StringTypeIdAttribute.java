package persistence.sql.ddl.attribute.id;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import persistence.sql.ddl.converter.SqlConverter;

import java.lang.reflect.Field;
import java.util.Optional;

public class StringTypeIdAttribute extends IdAttribute {
    private final String fieldName;
    private final Integer length;
    private final String columnName;
    private final String generateValueStrategy;

    public StringTypeIdAttribute(
            String fieldName,
            Integer length,
            String columnName,
            String generateValueStrategy,
            SqlConverter sqlConverter
    ) {
        super(sqlConverter);
        this.fieldName = fieldName;
        this.length = length;
        this.columnName = columnName;
        this.generateValueStrategy = generateValueStrategy;
        this.sqlConverter = sqlConverter;
    }

    public static StringTypeIdAttribute of(Field field, SqlConverter sqlConverter) {

        Optional<Column> columnOptional = Optional.ofNullable(field.getAnnotation(Column.class));

        return new StringTypeIdAttribute(
                field.getName(),
                columnOptional.map(Column::length).orElse(255),
                columnOptional.map(Column::name).orElse(field.getName()),
                Optional.ofNullable(field.getAnnotation(GeneratedValue.class))
                        .map(it -> sqlConverter.convert(it.strategy().getClass()))
                        .orElse(""),
                sqlConverter
        );
    }

    @Override
    public String makeComponent() {
        String component = (columnName.isBlank() ? fieldName : columnName) +
                " " + sqlConverter.convert(String.class) +
                String.format("(%s)", length) +
                " " + generateValueStrategy;
        return component.trim();
    }
}
