package persistence.sql.dialect;

import persistence.sql.model.SqlConstraint;
import persistence.sql.model.SqlType;

public interface Dialect {

    String getType(SqlType type);

    String getConstraint(SqlConstraint constraint);
}
