package persistence.sql.model.contstraint;

public class NullableConstraint implements SqlConstraint{

    private static final String NOT_NULL = "NOT NULL";

    private final boolean status;

    public NullableConstraint(boolean status) {
        this.status = status;
    }

    @Override
    public boolean getConstraintStatus() {
        return status;
    }

    @Override
    public String getConstraintSql() {
        return status ? "" : NOT_NULL;
    }
}
