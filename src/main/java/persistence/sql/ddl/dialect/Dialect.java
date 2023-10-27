package persistence.sql.ddl.dialect;

import persistence.sql.ddl.scheme.ColumnSchemes;

import java.util.HashMap;
import java.util.Map;

public abstract class Dialect {

    private final Map<Integer, String> types = new HashMap<>();
    private final Map<Integer, ColumnSchemes> schemes = new HashMap<>();

    protected void registerColumnType(int code, String name) {
        types.put(code, name);
    }

    protected void registerColumnScheme(int code, ColumnSchemes columnSchemes) {
        schemes.put(code, columnSchemes);
    }

    public String getType(final int typeCode) {
        final String result = types.get(typeCode);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect type mapping for type: " + typeCode);
        }

        return result;
    }

    public ColumnSchemes getSchemes(final int typeCode) {
        final ColumnSchemes result = schemes.get(typeCode);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect scheme mapping for type: " + typeCode);
        }

        return result;
    }

}
