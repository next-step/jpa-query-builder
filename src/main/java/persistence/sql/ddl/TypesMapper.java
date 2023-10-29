package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class TypesMapper {

    /**
     * Java Type <--> DB Column Type
     */
    private static final Map<String, Integer> typesMap = new HashMap<>() {{
        put("Long", Types.BIGINT);
        put("String", Types.VARCHAR);
        put("Integer", Types.INTEGER);
    }};

    public static Integer getFieldType(Field field) {
        final Class<?> fieldType = field.getType();

        return typesMap.get(fieldType.getSimpleName());
    }

}
