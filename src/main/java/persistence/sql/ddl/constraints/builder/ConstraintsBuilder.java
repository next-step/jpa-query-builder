package persistence.sql.ddl.constraints.builder;

import java.lang.reflect.Field;

public interface ConstraintsBuilder {
    String getConstraintsFrom(Field field);
}
