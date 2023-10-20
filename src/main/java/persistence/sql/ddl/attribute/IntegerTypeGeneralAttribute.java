package persistence.sql.ddl.attribute;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.sql.ddl.converter.SqlConverter;

import java.lang.reflect.Field;

public class IntegerTypeGeneralAttribute extends GeneralAttribute {
    private final boolean nullable;
    private final int scale;
    private final String fieldName;
    private final String columnName;

    private IntegerTypeGeneralAttribute(
            int scale,
            String fieldName,
            String columnName,
            boolean nullable,
            SqlConverter sqlConverter
    ) {
        super(sqlConverter);
        this.scale = scale;
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.nullable = nullable;
    }

    public static IntegerTypeGeneralAttribute of(Field field, SqlConverter sqlConverter) {
        Column column = field.getDeclaredAnnotation(Column.class);
        return new IntegerTypeGeneralAttribute(
                column.scale(),
                field.getName(),
                column.name(),
                field.isAnnotationPresent(Id.class),
                sqlConverter
        );
    }

    @Override
    public String makeComponent() {
        StringBuilder component = new StringBuilder();

        component.append(columnName.isBlank() ? fieldName : columnName);
        component.append(" ").append(sqlConverter.convert(Integer.class));
        if (scale != 0) {
            component.append(String.format(" (%s)", scale));
        }
        if (!nullable) {
            component.append(" NOT NULL");
        }
        return component.toString().trim();
    }
}
