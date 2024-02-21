package persistence.sql.ddl.dialect;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.dialect.column.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class Dialect {
    private final Map<Integer, String> columnDataTypes = new HashMap<>();
    private final Map<Nullable, String> columnNullableTypes = new HashMap<>();
    private final Map<GenerationType, String> pkGenerationTypes = new HashMap<>();

    public Dialect() {
        setDefaultColumnDataTypes();
        setDefaultNullableTypes();
        setDefaultPKGenerationTypes();
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

    protected abstract void setDefaultPKGenerationTypes();

    protected void registerPKGenerationTypes(GenerationType generationType, String name) {
        pkGenerationTypes.put(generationType, name);
    }

    public String getPKGenerationType(final Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            throw new IllegalStateException("Not PK Field");
        }
        GenerationType generationType = field.isAnnotationPresent(GeneratedValue.class)
                ? field.getAnnotation(GeneratedValue.class).strategy()
                : GenerationType.AUTO;
        String result = pkGenerationTypes.get(generationType);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect mapping for PKGenerationTYpe: " + generationType.name());
        }
        return result;
    }
}
