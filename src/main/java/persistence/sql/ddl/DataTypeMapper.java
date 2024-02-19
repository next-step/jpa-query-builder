package persistence.sql.ddl;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class DataTypeMapper {
    private static final Map<Class<?>, String> dataTypeMap = new HashMap<>();

    private DataTypeMapper() {
        dataTypeMap.put(Long.class, "BIGINT");
        dataTypeMap.put(String.class, "VARCHAR");
        dataTypeMap.put(Integer.class, "INTEGER");
    }

    public static String getDataType(Class<?> type) {
        return ofNullable(dataTypeMap.get(type))
                .orElseThrow(() -> new IllegalArgumentException("This type is not supported."));
    }

}
