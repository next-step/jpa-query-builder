package persistence.sql.ddl.attribute;

import jakarta.persistence.Column;
import persistence.sql.ddl.converter.SqlConverter;

import java.lang.reflect.Field;

public class LongTypeGeneralAttribute extends GeneralAttribute {
    private final Integer scale;
    private final String fieldName;
    private final String columnName;
    private final boolean nullable;

    private LongTypeGeneralAttribute(
            int scale,
            String fieldName,
            String columnName,
            boolean nullable,
            SqlConverter sqlConverter
    ) {
        super(sqlConverter);
        this.scale = scale;
        this.fieldName = fieldName;
        this.columnName = columnName != null && !columnName.isEmpty() ? columnName : fieldName;
        this.nullable = nullable;
    }

    public static LongTypeGeneralAttribute of(Field field, SqlConverter sqlConverter) {
        Column column = field.getDeclaredAnnotation(Column.class);
        return new LongTypeGeneralAttribute(
                column.scale(),
                field.getName(),
                column.name(),
                column.nullable(),
                sqlConverter
        );
    }

    @Override
    public String makeComponent() {
        StringBuilder component = new StringBuilder();

        component.append(columnName.isBlank() ? fieldName : columnName).append(" ");
        component.append(sqlConverter.convert(Long.class));
        if (scale != 0) {
            component.append(String.format(" (%s)", scale));
        }
        if (!nullable) {
            component.append(" NOT NULL");
        }
        return component.toString().trim();
    }
}
