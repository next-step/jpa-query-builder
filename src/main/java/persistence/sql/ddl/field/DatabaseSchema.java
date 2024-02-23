package persistence.sql.ddl.field;

import java.lang.reflect.Field;

public interface DatabaseSchema {
    String getName(Field field);

    String getType(Field field);

    String getConstraints(Field field);
}
