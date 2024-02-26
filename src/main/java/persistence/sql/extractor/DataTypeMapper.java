package persistence.sql.extractor;

import java.util.Map;

public class DataTypeMapper {
    private final Map<Class<?>, DataType> mappingTable = Map.of(
        Long.class, DataType.LONG,
        Integer.class, DataType.INT,
        String.class, DataType.STRING
    );

    public DataType map(Class<?> type) {
        DataType dataType = mappingTable.get(type);
        if(dataType == null) {
            throw new IllegalArgumentException("지원하지 않는 타입입니다.");
        }
        return mappingTable.get(type);
    }
}
