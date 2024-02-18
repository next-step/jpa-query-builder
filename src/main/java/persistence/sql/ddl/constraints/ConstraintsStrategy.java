package persistence.sql.ddl.constraints;

import java.lang.reflect.Field;

public interface ConstraintsStrategy {
    String getConstraintsFrom(Field field);
}
