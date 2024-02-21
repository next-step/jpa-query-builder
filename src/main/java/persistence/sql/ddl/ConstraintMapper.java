package persistence.sql.ddl;

import java.lang.reflect.Field;

public interface ConstraintMapper {

    String getConstraints(Field type);

}
