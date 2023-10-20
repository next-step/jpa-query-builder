package persistence.sql.ddl.attribute.id;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import persistence.sql.ddl.converter.SqlConverter;

import java.lang.reflect.Field;
import java.util.Optional;

public class LongTypeIdAttribute extends IdAttribute {
    private final String fieldName;
    private final String columnName;
    private final String generateValueStrategy;
    private final SqlConverter sqlConverter;

    private LongTypeIdAttribute(
            String fieldName,
            String columnName,
            String generateValueStrategy,
            SqlConverter sqlConverter
    ) {
        super(sqlConverter);
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.generateValueStrategy = generateValueStrategy;
        this.sqlConverter = sqlConverter;
    }

    public static LongTypeIdAttribute of(Field field, SqlConverter sqlConverter) {
        return new LongTypeIdAttribute(
                field.getName(),
                Optional.ofNullable(field.getAnnotation(Column.class)).map(Column::name).orElse(""),
                Optional.ofNullable(field.getAnnotation(GeneratedValue.class))
                        .map(it -> sqlConverter.convert(it.strategy().getClass()))
                        .orElse(""),
                sqlConverter
        );
    }

    @Override
    public String makeComponent() {
        String component = (columnName.isBlank() ? fieldName : columnName) + " " +
                sqlConverter.convert(Long.class) + " " +
                generateValueStrategy;
        return component.trim();
    }
}
