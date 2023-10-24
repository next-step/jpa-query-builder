package persistence.sql.ddl.type;

import persistence.sql.ddl.exception.DataTypeNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class H2DataTypeMapper implements DataTypeMapper {

    private final Map<Class<?>, DataType> dataTypes = new HashMap<>();

    private H2DataTypeMapper() {
        dataTypes.put(Long.class, new H2DataType("BIGINT"));
        dataTypes.put(Integer.class, new H2DataType("INTEGER"));
        dataTypes.put(String.class, new H2DataType("VARCHAR"));
    }

    @Override
    public DataType getDataType(Class<?> clazz) {
        return Optional.ofNullable(dataTypes.get(clazz))
                .orElseThrow(DataTypeNotFoundException::new);
    }

    public static H2DataTypeMapper getInstance() {
        return H2DataTypeMapperHolder.INSTANCE;
    }

    private static class H2DataTypeMapperHolder {
        private static final H2DataTypeMapper INSTANCE = new H2DataTypeMapper();
    }
}
