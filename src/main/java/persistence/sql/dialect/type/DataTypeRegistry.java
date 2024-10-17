package persistence.sql.dialect.type;

import persistence.model.meta.DataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class DataTypeRegistry {
    protected final Map<Class<?>, DataType> registry = new HashMap<>();

    protected DataTypeRegistry() {
        registerDataType();
    }

    private void registerDataType() {
        List<Integer> typeCodes = getMappingSqlCodes();

        for (Integer typeCode : typeCodes) {
            Class<?> javaType = mapSqlCodeToJavaType(typeCode);
            String typeNamePattern = mapSqlCodeToNamePattern(typeCode);

            DataType dataType = new DataType(typeCode, javaType, typeNamePattern);
            registry.put(javaType, dataType);
        }
    }

    abstract List<Integer> getMappingSqlCodes();

    abstract Class<?> mapSqlCodeToJavaType(int typeCode);

    abstract String mapSqlCodeToNamePattern(int typeCode);

    public DataType getDataType(Class<?> javaType) {
        DataType dataType = registry.get(javaType);
        if (dataType == null) {
            throw new NoSuchElementException("UNSUPPORTED JAVA TYPE : " + javaType.getSimpleName());
        }
        return dataType;
    }
}
