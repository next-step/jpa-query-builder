package domain.step3.vo;

import java.sql.Types;
import java.util.HashMap;

public class JavaMappingType {

    private final HashMap<Integer, Class<?>> javaTypeToClassMap;
    private final HashMap<Class<?>, Integer> javaClassToTypeMap;

    public JavaMappingType() {
        this.javaTypeToClassMap = javaTypeToClassMap();
        this.javaClassToTypeMap = javaClassToTypeMap();
    }

    private HashMap<Integer, Class<?>> javaTypeToClassMap() {
        HashMap<Integer, Class<?>> map = new HashMap<>();
        map.put(Types.VARCHAR, String.class);
        map.put(Types.INTEGER, Integer.class);
        map.put(Types.BIGINT, Long.class);
        return map;
    }

    private HashMap<Class<?>, Integer> javaClassToTypeMap() {
        HashMap<Class<?>, Integer> map = new HashMap<>();
        map.put(String.class, Types.VARCHAR);
        map.put(Integer.class, Types.INTEGER);
        map.put(Long.class, Types.BIGINT);
        return map;
    }

    public Class<?> getClassByType(Integer javaType) {
        return javaTypeToClassMap.get(javaType);
    }

    public Integer getJavaTypeByClass(Class<?> clazz) {
        return javaClassToTypeMap.get(clazz);
    }

    public void checkJavaType(Integer javaType) {
        if (!javaTypeToClassMap.containsKey(javaType)) {
            throw new IllegalStateException("지원하는 javaType 이 아닙니다.");
        }
    }
}
