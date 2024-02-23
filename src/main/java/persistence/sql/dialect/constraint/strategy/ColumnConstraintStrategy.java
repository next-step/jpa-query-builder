package persistence.sql.dialect.constraint.strategy;

import java.lang.reflect.Field;

public interface ColumnConstraintStrategy {

    String generateConstraints(Field field);
}
