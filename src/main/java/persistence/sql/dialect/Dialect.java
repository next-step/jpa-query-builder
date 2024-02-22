package persistence.sql.dialect;

import persistence.sql.model.SqlConstraint;
import persistence.sql.model.SqlType;

import java.util.Arrays;
import java.util.HashMap;

public abstract class Dialect {

    private final HashMap<SqlType, String> types = buildDefaultTypes();
    private final HashMap<SqlConstraint, String> constraints = buildDefaultConstraints();

    private HashMap<SqlType, String> buildDefaultTypes() {
        HashMap<SqlType, String> types = new HashMap<>();

        SqlType[] sqlTypes = SqlType.values();
        Arrays.stream(sqlTypes)
                .forEach(sqlType -> {
                    String query = sqlType.query();
                    types.put(sqlType, query);
                });

        return types;
    }

    private HashMap<SqlConstraint, String> buildDefaultConstraints() {
        HashMap<SqlConstraint, String> constraints = new HashMap<>();

        SqlConstraint[] sqlConstraints = SqlConstraint.values();
        Arrays.stream(sqlConstraints)
                .forEach(sqlConstraint -> {
                    String query = sqlConstraint.query();
                    constraints.put(sqlConstraint, query);
                });

        return constraints;
    }

    protected void registerDialect(SqlType type, String query) {
        types.put(type, query);
    }

    protected void registerDialect(SqlConstraint constraint, String query) {
        constraints.put(constraint, query);
    }

    public String getType(final SqlType type) {
        final String result = types.get(type);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect mapping for type: " + type.name());
        }
        return result;
    }

    public String getConstraint(final SqlConstraint constraint) {
        final String result = constraints.get(constraint);
        if (result == null) {
            throw new IllegalArgumentException("No Dialect mapping for constraint: " + constraint.name());
        }
        return result;
    }
}
