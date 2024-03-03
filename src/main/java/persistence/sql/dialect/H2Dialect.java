package persistence.sql.dialect;

import java.util.Map;
import persistence.meta.vo.EntityField;

public class H2Dialect implements Dialect {
    private static final Map<Class<?>, String> fieldTypeMap = Map.of(
        String.class, "varchar",
        Integer.class, "integer",
        Long.class, "bigint"
    );

    @Override
    public void getDialectType() {
        return;
    }

    @Override
    public String getFieldType(EntityField entityField) {
        String result = fieldTypeMap.get(entityField.getFieldType());
        if (result == null) {
            throw new RuntimeException("지원하지 않는 필드타입입니다");
        }
        return result;
    }


}
