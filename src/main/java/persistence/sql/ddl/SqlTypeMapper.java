package persistence.sql.ddl;

import java.lang.reflect.Field;

public interface SqlTypeMapper {
    String getSqlType(Field field);
}
