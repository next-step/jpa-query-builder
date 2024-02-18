package persistence.sql.ddl.constraints.strategy;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.ddl.constraints.builder.ConstraintsBuilder;
import persistence.sql.ddl.constraints.builder.NotNullConstraintsBuilder;
import persistence.sql.ddl.constraints.builder.UniqueConstraintsBuilder;

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
