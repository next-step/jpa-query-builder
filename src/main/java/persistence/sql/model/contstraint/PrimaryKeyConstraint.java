package persistence.sql.model.contstraint;

public class PrimaryKeyConstraint implements SqlConstraint{

    private static final String PRIMARY_KEY = "PRIMARY KEY";

    private final boolean status;

    public PrimaryKeyConstraint(boolean status) {
        this.status = status;
    }

    @Override
    public boolean getConstraintStatus() {
        return status;
    }

    @Override
    public String getConstraintSql() {
        return status ? PRIMARY_KEY : "";
    }

}
