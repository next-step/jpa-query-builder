package persistence.sql.ddl;

import java.lang.reflect.Field;

public class GeneratedValueH2Constraint extends H2Constraint {

    private static final String AUTO_INCREMENT_CONSTRAINT_QUERY = "AUTO_INCREMENT";

    @Override
    public String getConstraintQuery(Field type) {
        return AUTO_INCREMENT_CONSTRAINT_QUERY;
    }

}
