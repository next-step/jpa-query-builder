package persistence.sql.ddl.mapper;

import java.lang.reflect.Field;

public interface TypeMapper {

    String EMPTY_STRING = "";

    String getType(Field field);

}
