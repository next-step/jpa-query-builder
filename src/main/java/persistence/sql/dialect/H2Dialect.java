package persistence.sql.dialect;

import persistence.sql.model.SqlConstraint;
import persistence.sql.model.SqlType;

import java.util.Arrays;
import java.util.HashMap;

public class H2Dialect implements Dialect {

    private final HashMap<SqlType, String> types = buildDefaultTypes();
    private final HashMap<SqlConstraint, String> constraints = buildDefaultConstraints();

    public H2Dialect() {
    }

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

    @Override
    public String getType(SqlType type) {
        return null;
    }

    @Override
    public String getConstraint(SqlConstraint constraint) {
        return null;
    }
}
