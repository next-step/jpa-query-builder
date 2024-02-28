package persistence.sql.model;

import java.lang.reflect.Field;
import java.util.List;

public interface BaseColumn {

    Field getField();

    String getName();

    SqlType getType();

    List<SqlConstraint> getConstraints();
}
