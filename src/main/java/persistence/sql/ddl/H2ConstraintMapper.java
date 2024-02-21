package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.util.Map;
import java.util.Optional;

public class H2ConstraintMapper implements ConstraintMapper {

    private static final String EMPTY_STRING = "";

    private final Map<Class<?>, String> constraints = Map.of(
            Id.class, "PRIMARY KEY"
    );

    @Override
    public String getConstraint(Class<?> type) {
        return Optional.ofNullable(constraints.get(type))
                .orElse(EMPTY_STRING);
    }

}
