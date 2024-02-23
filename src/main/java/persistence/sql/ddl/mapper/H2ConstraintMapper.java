package persistence.sql.ddl.mapper;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import persistence.sql.ddl.domain.Constraints;
import persistence.sql.ddl.constraint.ColumnH2Constraint;
import persistence.sql.ddl.constraint.GeneratedValueH2Constraint;
import persistence.sql.ddl.constraint.H2Constraint;
import persistence.sql.ddl.constraint.IdH2Constraint;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class H2ConstraintMapper implements ConstraintMapper {

    private static final Map<Class<?>, Class<? extends H2Constraint>> constraints = Map.of(
            Id.class, IdH2Constraint.class,
            GeneratedValue.class, GeneratedValueH2Constraint.class,
            Column.class, ColumnH2Constraint.class
    );

    @Override
    public Constraints getConstraints(Field field) {
        return new Constraints(Arrays.stream(field.getDeclaredAnnotations())
                .filter(annotation -> constraints.containsKey(annotation.annotationType()))
                .map(annotation -> {
                    try {
                        return constraints.get(annotation.annotationType()).getDeclaredConstructor(Field.class).newInstance(field);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList()));
    }

}
