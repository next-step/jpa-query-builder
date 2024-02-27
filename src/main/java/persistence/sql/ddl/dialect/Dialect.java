package persistence.sql.ddl.dialect;

import persistence.sql.meta.column.ColumnType;
import persistence.sql.meta.column.Nullable;
import persistence.sql.meta.pk.GenerationType;

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

    protected abstract void setDefaultNullableTypes();

    protected abstract void setDefaultPKGenerationTypes();


    protected void registerColumnDataType(int code, String name) {
        columnDataTypes.put(code, name);
    }

    public String getColumnDataType(final ColumnType columnType) {
        final int typeCode = columnType.getType();
        final String result = columnDataTypes.get(columnType.getType());
        if (result == null) {
            throw new IllegalArgumentException("No Dialect mapping for type: " + typeCode);
        }
        return result;
    }

    protected void registerColumnNullableTypes(Nullable nullable, String name) {
        columnNullableTypes.put(nullable, name);
    }

    public String getColumnNullableType(final Nullable nullable) {
        String result = columnNullableTypes.get(nullable);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect mapping for nullableType: " + nullable.name());
        }
        return result;
    }

    protected void registerPKGenerationTypes(GenerationType generationType, String name) {
        pkGenerationTypes.put(generationType, name);
    }

    public String getPKGenerationType(final GenerationType generationType) {
        String result = pkGenerationTypes.get(generationType);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect mapping for PKGenerationTYpe: " + generationType.name());
        }
        return result;
    }
}
