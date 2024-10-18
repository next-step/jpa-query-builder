package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class H2SqlTypeMapper implements SqlTypeMapper {

    Map<String, String> sqlType;

    public H2SqlTypeMapper() {
        sqlType = new HashMap<>();
        sqlType.put("Long", "BIGINT");
        sqlType.put("String", "VARCHAR(255)");
        sqlType.put("Integer", "INT");
    }

    @Override
    public String getSqlType(Field field) {
        return sqlType.getOrDefault(field.getType().getSimpleName(), field.getType().getSimpleName());
    }

}