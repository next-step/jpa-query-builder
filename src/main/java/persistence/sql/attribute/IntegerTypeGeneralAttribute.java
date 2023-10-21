package persistence.sql.attribute;

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
            boolean nullable
    ) {
        this.scale = scale;
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.nullable = nullable;
    }

    public static IntegerTypeGeneralAttribute of(Field field) {
        Column column = field.getDeclaredAnnotation(Column.class);
        return new IntegerTypeGeneralAttribute(
                column.scale(),
                field.getName(),
                column.name(),
                field.isAnnotationPresent(Id.class)
        );
    }

    @Override
    public String prepareDDL(SqlConverter sqlConverter) {
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

    @Override
    public String getColumnName() {
        return this.columnName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public boolean isNullable() {
        return this.nullable;
    }
}
