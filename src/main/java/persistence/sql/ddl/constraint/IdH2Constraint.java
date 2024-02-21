package persistence.sql.ddl.constraint;

import java.lang.reflect.Field;

public class IdH2Constraint extends H2Constraint {

    private static final String PRIMARY_KEY_CONSTRAINT_QUERY = "PRIMARY KEY";

    @Override
    public String getConstraintQuery(Field type) {
        return PRIMARY_KEY_CONSTRAINT_QUERY;
    }

}
