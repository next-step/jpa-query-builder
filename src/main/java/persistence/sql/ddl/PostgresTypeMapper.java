package persistence.sql.ddl;

import java.lang.reflect.Field;

public class PostgresTypeMapper implements SqlTypeMapper{

    @Override
    public String getSqlType(Field field) {
        if("Long".equals(field.getType().getSimpleName())) {
            return "BIGINT";
        } else if("String".equals(field.getType().getSimpleName())) {
            return "VARCHAR(255)";
        } else if("Integer".equals(field.getType().getSimpleName())) {
            return "INTEGER";
        }
        return field.getType().getSimpleName();
    }
}
