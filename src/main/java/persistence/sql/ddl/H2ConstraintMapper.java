package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class H2ConstraintMapper implements ConstraintMapper {

    private static final String SPACE = " ";

    private static final Map<Class<?>, H2Constraint> constraints = Map.of(
            Id.class, new IdH2Constraint(),
            GeneratedValue.class, new GeneratedValueH2Constraint(),
            Column.class, new ColumnH2Constraint()
    );

    @Override
    public String getConstraints(Field field) {
        return Arrays.stream(field.getDeclaredAnnotations())
                .filter(annotation -> constraints.containsKey(annotation.annotationType()))
                .map(annotation -> constraints.get(annotation.annotationType()).getConstraintQuery(field))
                .collect(Collectors.joining(SPACE));
    }

}
