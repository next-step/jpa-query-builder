package persistence.sql.dialect.type;

import persistence.model.meta.DataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class DataTypeRegistry {
    protected final Map<Class<?>, DataType> registry = new HashMap<>();

    DataTypeMappingStrategy dataTypeMappingStrategy;

    public DataTypeRegistry(DataTypeMappingStrategy dataTypeMappingStrategy) {
        this.dataTypeMappingStrategy = dataTypeMappingStrategy;
        registerDataType();
    }

    private void registerDataType() {
        List<Integer> typeCodes = dataTypeMappingStrategy.getMappingSqlCodes();

        for (Integer typeCode : typeCodes) {
            Class<?> javaType = dataTypeMappingStrategy.mapSqlCodeToJavaType(typeCode);
            String typeNamePattern = dataTypeMappingStrategy.mapSqlCodeToNamePattern(typeCode);

            DataType dataType = new DataType(typeCode, javaType, typeNamePattern);
            registry.put(javaType, dataType);
        }
    }

    public DataType getDataType(Class<?> javaType) {
        DataType dataType = registry.get(javaType);
        if (dataType == null) {
            throw new NoSuchElementException("UNSUPPORTED JAVA TYPE : " + javaType.getSimpleName());
        }
        return dataType;
    }
}
