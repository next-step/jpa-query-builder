package persistence.sql.dialect;

import persistence.sql.JdbcUtils;
import persistence.sql.dialect.column.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class Dialect {
    private final Map<Integer, String> columnDataTypes = new HashMap<>();
    private final Map<Nullable, String> columnNullableTypes = new HashMap<>();

    public Dialect() {
        setDefaultColumnDataTypes();
        setDefaultNullableTypes();
    }

    protected abstract void setDefaultColumnDataTypes();

    protected void registerColumnDataType(int code, String name) {
        columnDataTypes.put(code, name);
    }

    public String getColumnDataType(final Field field) {
        Integer typeCode = JdbcUtils.convertJavaClassToJdbcTypeCode(field.getType());
        return getColumnDataType(typeCode);
    }

    public String getColumnDataType(final int typeCode) {
        final String result = columnDataTypes.get(typeCode);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect mapping for type: " + typeCode);
        }
        return result;
    }


    protected abstract void setDefaultNullableTypes();

    protected void registerColumnNullableTypes(Nullable nullable, String name) {
        columnNullableTypes.put(nullable, name);
    }

    public String getColumnNullableType(final Field field) {
        Nullable nullable = Nullable.getNullable(field);
        String result = columnNullableTypes.get(nullable);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect mapping for nullableType: " + nullable.name());
        }
        return result;
    }


}
