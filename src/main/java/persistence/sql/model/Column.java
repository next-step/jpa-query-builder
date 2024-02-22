package persistence.sql.model;

import java.util.Collections;
import java.util.List;

public class Column {

    private final SqlType type;

    private final String name;

    private final List<SqlConstraint> constraints;

    public Column(SqlType type, String name, List<SqlConstraint> constraints) {
        this.type = type;
        this.name = name;
        this.constraints = constraints;
    }

    public SqlType type() {
        return type;
    }

    public String name() {
        return name;
    }

    public List<SqlConstraint> constraints() {
        return Collections.unmodifiableList(constraints);
    }
}
