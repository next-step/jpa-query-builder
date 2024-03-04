package persistence.sql.dialect.constraint.strategy;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ColumnConstraintStrategy {

    String generateConstraints(List<Annotation> annotations);
}
