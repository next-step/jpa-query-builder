package persistence.sql.ddl.mapper;

import java.lang.reflect.Field;

public interface TypeMapper {

    String getType(Field field);

}
