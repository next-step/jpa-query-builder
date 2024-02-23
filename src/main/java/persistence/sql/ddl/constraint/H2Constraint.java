package persistence.sql.ddl.constraint;

import java.lang.reflect.Field;

public interface H2Constraint {

    String EMPTY_STRING = "";

    String generateConstraintQuery(Field field);

    String getConstraintQuery();

}
