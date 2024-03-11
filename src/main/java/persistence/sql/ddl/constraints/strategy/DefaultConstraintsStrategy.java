package persistence.sql.ddl.constraints.strategy;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.ddl.common.StringConstants;
import persistence.sql.ddl.constraints.ConstraintsTranslator;
import persistence.sql.ddl.constraints.impl.AutoIncrementConstraintsTranslator;
import persistence.sql.ddl.constraints.impl.NotNullConstraintsTranslator;
import persistence.sql.ddl.constraints.impl.UniqueConstraintsTranslator;

public class DefaultConstraintsStrategy implements ConstraintsStrategy {
    private final List<ConstraintsTranslator> constraintsTranslators = List.of(
        new AutoIncrementConstraintsTranslator(),
        new UniqueConstraintsTranslator(),
        new NotNullConstraintsTranslator()
    );

    @Override
    public String getConstraintsFrom(Field field) {
        return constraintsTranslators.stream()
            .filter(constraintsTranslator -> constraintsTranslator.supports(field))
            .map(constraintsTranslator -> constraintsTranslator.getConstraintsFrom(field))
            .collect(Collectors.joining(StringConstants.SPACE));
    }
}
