package persistence.sql.ddl.domain;

import persistence.sql.ddl.constraint.Constraint;
import persistence.sql.ddl.constraint.PrimaryKey;

import java.util.List;

public class Constraints {

    private final List<Constraint> constraints;

    public Constraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public boolean hasPrimaryKeyConstraint() {
        return constraints.stream()
                .anyMatch(PrimaryKey.class::isInstance);
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }
}
