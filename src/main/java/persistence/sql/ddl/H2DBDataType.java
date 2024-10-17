package persistence.sql.ddl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class H2DBDataType {
    private static final Map<Class<?>, String> typeMap = new HashMap<>();

    static {
        typeMap.put(Long.class, "BIGINT");
        typeMap.put(long.class, "BIGINT");
        typeMap.put(Integer.class, "INTEGER");
        typeMap.put(int.class, "INTEGER");
        typeMap.put(String.class, "VARCHAR");
        typeMap.put(Boolean.class, "BIT");
        typeMap.put(boolean.class, "BIT");
        typeMap.put(Double.class, "DOUBLE");
        typeMap.put(double.class, "DOUBLE");
        typeMap.put(Float.class, "REAL");
        typeMap.put(float.class, "REAL");
        typeMap.put(Date.class, "DATE");
    }

    public static String castType(Class<?> columnType) {
        return castType(columnType, 255);
    }

    public static String castType(Class<?> columnType, int length) {
        String sqlType = typeMap.get(columnType);

        if (columnType == String.class) {
            return sqlType + "(" + length + ")";
        }

        if (sqlType == null) {
            throw new IllegalArgumentException("Unsupported data type: " + columnType.getName());
        }

        return sqlType;
    }
}
