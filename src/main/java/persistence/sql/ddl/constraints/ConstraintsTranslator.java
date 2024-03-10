package persistence.sql.ddl.constraints;

import java.lang.reflect.Field;

public interface ConstraintsTranslator {
    String getConstraintsFrom(Field field);

    boolean supports(Field field);
}
