package persistence.sql.ddl.component.column;

import java.util.HashMap;
import java.util.Map;

public class DataTypeConverter {
    private static final Map<Class<?>, String> dataTypeMap = new HashMap<>();

    static {
        dataTypeMap.put(Integer.class, "integer");
        dataTypeMap.put(Long.class, "bigint");
        dataTypeMap.put(String.class, "varchar(100)");
        // TODO : 이외 타입 아직 미지원
    }

    public static String convert(Class<?> clazz) {
        if (!dataTypeMap.containsKey(clazz)) {
            throw new IllegalArgumentException("Data type not supported! (yet, or ever)");
        }
        return dataTypeMap.get(clazz);
    }

    public static String setDataLength(Class<String> clazz) {
        // TODO : Field 에 애너테이션으로 길이 지정돼있을 경우 (N)으로 치환해주는 로직 추가 예정
        throw new IllegalArgumentException("Method not supported yet!");
    }
}
