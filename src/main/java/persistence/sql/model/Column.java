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

    @Override
    public int hashCode() {
        int result = Integer.hashCode(type.hashCode());
        result = 31 * result + Integer.hashCode(name.hashCode());
        result = 31 * result + Integer.hashCode(constraints.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Column)) {
            return false;
        }

        Column column = (Column) obj;
        return column.type.equals(this.type)
                && column.name.equals(this.name)
                && column.constraints.equals(this.constraints);

    }
}
