package persistence.sql.ddl.constraints.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.ddl.constraints.ConstraintsBuilder;
import persistence.sql.ddl.constraints.ConstraintsStrategy;

public class DefaultConstraintsStrategy implements ConstraintsStrategy {
    private final List<ConstraintsBuilder> constraintsBuilders = List.of(
        new UniqueConstraintsBuilder(),
        new NotNullConstraintsBuilder()
    );

    @Override
    public String getConstraintsFrom(Field field) {
        return constraintsBuilders.stream()
            .map(constraintsBuilder -> constraintsBuilder.getConstraintsFrom(field))
            .collect(Collectors.joining(" "));
    }
}
