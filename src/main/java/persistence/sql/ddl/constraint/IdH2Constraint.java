package persistence.sql.ddl.constraint;

import java.lang.reflect.Field;

public class IdH2Constraint implements H2Constraint {

    private static final String PRIMARY_KEY_CONSTRAINT_QUERY = "PRIMARY KEY";

    private final String constraintQuery;

    public IdH2Constraint(Field field) {
        this.constraintQuery = generateConstraintQuery(field);
    }

    @Override
    public String generateConstraintQuery(Field field) {
        return PRIMARY_KEY_CONSTRAINT_QUERY;
    }

    @Override
    public String getConstraintQuery() {
        return constraintQuery;
    }
}
