package persistence.sql.datatype;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class JavaType {
    private Map<Class<?>, Integer> map;

    public JavaType() {
        map = new HashMap<>();
        map.put(Integer.class, Types.INTEGER);
        map.put(Long.class, Types.BIGINT);
        map.put(String.class, Types.VARCHAR);
    }

    public Integer getTypeConst(Class<?> javaType) {
        return map.get(javaType);
    }
}
