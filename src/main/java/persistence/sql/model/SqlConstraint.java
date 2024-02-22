package persistence.sql.model;

import jakarta.persistence.Id;

import java.util.HashMap;

public enum SqlConstraint {
    NOT_NULL,
    UNIQUE,
    PRIMARY_KEY,
    FOREIGN_KEY,
    CHECK;

    private static final HashMap<Class<?>, SqlConstraint> javaClassToJdbcConstraintCodeMap = buildJavaClassToJdbcConstraintCodeMappings();

    private static HashMap<Class<?>, SqlConstraint> buildJavaClassToJdbcConstraintCodeMappings() {
        final HashMap<Class<?>, SqlConstraint> workMap = new HashMap<>();

        workMap.put(Id.class, SqlConstraint.PRIMARY_KEY);

        return workMap;
    }

    public static SqlConstraint of(Class<?> clazz) {
        SqlConstraint constraint = javaClassToJdbcConstraintCodeMap.get(clazz);

        if (constraint == null) {
            throw new IllegalArgumentException("No mapping for jdbc constraint: " + clazz.getSimpleName());
        }

        return constraint;
    }

    public String getQuery() {
        return this.name()
                .replace('_', ' ')
                .toLowerCase();
    }
}
