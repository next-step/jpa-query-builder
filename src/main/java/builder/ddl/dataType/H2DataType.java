package builder.ddl.dataType;

import java.util.HashMap;
import java.util.Map;

public class H2DataType implements DataType{

    private static final Map<Class<?>, String> dataTypeMap = new HashMap<>();

    static {
        dataTypeMap.put(String.class, "VARCHAR(255)");
        dataTypeMap.put(Integer.class, "INTEGER");
        dataTypeMap.put(Long.class, "BIGINT");
    }

    private final static String NOT_ALLOWED_DATATYPE = "지원하지 않은 데이터타입입니다. DataType: ";

    @Override
    public String findDataTypeByClass(Class<?> dataType) {
        String h2DataType = dataTypeMap.get(dataType);
        if (h2DataType == null) {
            throw new IllegalArgumentException(NOT_ALLOWED_DATATYPE + dataType);
        }
        return h2DataType;
    }
}
