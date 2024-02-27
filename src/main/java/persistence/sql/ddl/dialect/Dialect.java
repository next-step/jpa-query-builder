package persistence.sql.ddl.dialect;

import persistence.sql.ddl.constraint.Constraint;
import persistence.sql.ddl.domain.Type;

public interface Dialect {

    String getTypeString(Type type, int length);

    String getConstraintString(Constraint constraint);

}
