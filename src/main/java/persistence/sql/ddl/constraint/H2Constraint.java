package persistence.sql.ddl.constraint;

import java.lang.reflect.Field;

public abstract class H2Constraint {
    public abstract String getConstraintQuery(Field type);

}
