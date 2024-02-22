package persistence.sql.model.contstraint;

public interface SqlConstraint {

    boolean getConstraintStatus();

    String getConstraintSql();

}