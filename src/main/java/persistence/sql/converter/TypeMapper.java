package persistence.sql.converter;

import persistence.sql.constant.BasicColumnType;
import persistence.sql.constant.ClassType;

import java.util.HashMap;
import java.util.Map;

public class TypeMapper {

    private final Map<ClassType, BasicColumnType> typeMap = new HashMap<>();

    public TypeMapper() {
        typeMap.put(ClassType.STRING, BasicColumnType.VARCHAR);
        typeMap.put(ClassType.LONG, BasicColumnType.BIGINT);
        typeMap.put(ClassType.BOOLEAN, BasicColumnType.BOOLEAN);
        typeMap.put(ClassType.INTEGER, BasicColumnType.INTEGER);
    }

    public BasicColumnType getBasicColumnType(ClassType type) {
        return typeMap.get(type);
    }

}
