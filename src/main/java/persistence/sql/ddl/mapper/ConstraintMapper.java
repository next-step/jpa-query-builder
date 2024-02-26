package persistence.sql.ddl.mapper;

import persistence.sql.ddl.domain.Constraint;

public interface ConstraintMapper {
    String getConstraintString(Constraint constraint);

}
