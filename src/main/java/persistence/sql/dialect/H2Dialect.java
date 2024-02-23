package persistence.sql.dialect;

import persistence.sql.model.SqlConstraint;
import persistence.sql.model.SqlType;

import java.util.HashMap;

public class H2Dialect implements Dialect {

    private final HashMap<SqlType, String> types;
    private final HashMap<SqlConstraint, String> constraints;

    public H2Dialect() {
        types = buildTypes();
        constraints = buildConstraints();
    }

    private HashMap<SqlType, String> buildTypes() {
        HashMap<SqlType, String> types = new HashMap<>();
        types.put(SqlType.VARCHAR, "varchar");
        types.put(SqlType.BIGINT, "bigint");
        types.put(SqlType.INTEGER, "integer");
        return types;
    }

    private HashMap<SqlConstraint, String> buildConstraints() {
        HashMap<SqlConstraint, String> constraints = new HashMap<>();
        constraints.put(SqlConstraint.PRIMARY_KEY, "primary key");
        constraints.put(SqlConstraint.IDENTITY, "auto_increment");
        constraints.put(SqlConstraint.NOT_NULL, "not null");
        return constraints;
    }

    @Override
    public String getType(SqlType type) {
        String findType = types.get(type);

        if (findType == null) {
            throw new IllegalArgumentException("Not supported for type: " + type.name());
        }

        return findType;
    }

    @Override
    public String getConstraint(SqlConstraint constraint) {
        String findConstraint = constraints.get(constraint);

        if (findConstraint == null) {
            throw new IllegalArgumentException("Not supported for constraint: " + constraint.name());
        }

        return findConstraint;
    }
}
