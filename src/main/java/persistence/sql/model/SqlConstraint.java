package persistence.sql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

public enum SqlConstraint {
    NOT_NULL,
    UNIQUE,
    PRIMARY_KEY,
    FOREIGN_KEY,
    CHECK;

    public static List<SqlConstraint> of(Column column) {
        List<SqlConstraint> constraints = new ArrayList<>();

        if (!column.nullable()) {
            constraints.add(NOT_NULL);
        }

        return constraints;
    }

    public static List<SqlConstraint> of(Id id) {
        List<SqlConstraint> constraints = new ArrayList<>();

        constraints.add(PRIMARY_KEY);

        return constraints;
    }
}
