package persistence.sql.converter;

import persistence.sql.constant.ColumnType;

import java.util.HashMap;
import java.util.Map;

public class TypeMapper {

    private final Map<Class<?>, ColumnType> typeMap = new HashMap<>();

    public TypeMapper() {
        typeMap.put(String.class, ColumnType.VARCHAR);
        typeMap.put(Long.class, ColumnType.BIGINT);
        typeMap.put(Boolean.class, ColumnType.BOOLEAN);
        typeMap.put(Integer.class, ColumnType.INTEGER);
    }

    public ColumnType getBasicColumnType(Class<?> type) {
        return typeMap.get(type);
    }

}
