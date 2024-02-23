package persistence.sql.ddl.mapper;

import persistence.sql.ddl.domain.Constraints;

import java.lang.reflect.Field;

public interface ConstraintMapper {
    Constraints getConstraints(Field type);

}
