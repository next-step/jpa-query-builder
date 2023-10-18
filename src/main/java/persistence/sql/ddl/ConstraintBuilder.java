package persistence.sql.ddl;

public class ConstraintBuilder {
    private final static String PRIMARY_CONSTRAINT = " PRIMARY KEY";

    public ConstraintBuilder() {
    }

    public String buildPrimaryKey(Constraint constraint) {
        if(constraint.isPrimaryKey()) {
            return PRIMARY_CONSTRAINT;
        }

        return "";
    }
}
