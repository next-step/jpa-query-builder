package persistence.sql.ddl.domain;

import persistence.sql.ddl.constraint.H2Constraint;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class Constraints {

    private static final String SPACE = " ";

    private final List<H2Constraint> constraints;
    private final Field field;

    public Constraints(List<H2Constraint> constraints, Field field) {
        this.constraints = constraints;
        this.field = field;
    }

    public String getConstraintsQuery() {
        return constraints.stream()
                .map(constraint -> constraint.getConstraintQuery(field))
                .collect(Collectors.joining(SPACE));
    }

}
