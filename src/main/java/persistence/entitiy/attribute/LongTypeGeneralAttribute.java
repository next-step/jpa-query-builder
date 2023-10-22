package persistence.entitiy.attribute;

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
            boolean nullable
    ) {
        this.scale = scale;
        this.fieldName = fieldName;
        this.columnName = columnName != null && !columnName.isEmpty() ? columnName : fieldName;
        this.nullable = nullable;
    }

    public static LongTypeGeneralAttribute of(Field field) {
        Column column = field.getDeclaredAnnotation(Column.class);
        return new LongTypeGeneralAttribute(
                column.scale(),
                field.getName(),
                column.name(),
                column.nullable()
        );
    }

    @Override
    public String prepareDDL(SqlConverter sqlConverter) {
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
        return nullable;
    }
}
