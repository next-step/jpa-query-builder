package persistence.sql.ddl.mapping;

import java.lang.reflect.Field;

public interface PrimaryKeyGenerationType {

    String value(Field field);

}
