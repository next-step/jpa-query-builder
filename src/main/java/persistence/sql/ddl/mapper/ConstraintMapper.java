package persistence.sql.ddl.mapper;

import java.lang.reflect.Field;

public interface ConstraintMapper {

    String SPACE = " ";

    String getConstraints(Field type);

}