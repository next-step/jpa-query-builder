package persistence.sql.ddl;

import java.lang.reflect.Field;

public interface TypeMapper {

    String getType(Field field);

}
