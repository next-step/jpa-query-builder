package persistence.sql.ddl.constraints.strategy;

import java.lang.reflect.Field;

public interface ConstraintsStrategy {
    String getConstraintsFrom(Field field);
}
