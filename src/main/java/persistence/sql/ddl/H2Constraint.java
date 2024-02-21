package persistence.sql.ddl;

import java.lang.reflect.Field;

public abstract class H2Constraint {

    static final String EMPTY_STRING = "";

    public abstract String getConstraintQuery(Field type);

}
