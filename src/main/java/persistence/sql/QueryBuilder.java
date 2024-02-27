package persistence.sql;

import jakarta.persistence.Transient;
import persistence.sql.ddl.mapper.ConstraintMapper;
import persistence.sql.ddl.mapper.H2ConstraintMapper;
import persistence.sql.ddl.mapper.H2TypeMapper;
import persistence.sql.ddl.mapper.TypeMapper;

import java.lang.reflect.Field;

public interface QueryBuilder {

    String COMMA = ", ";
    String SPACE = " ";

    TypeMapper TYPE_MAPPER = new H2TypeMapper();
    ConstraintMapper CONSTRAINT_MAPPER = new H2ConstraintMapper();

    String build();

    default boolean isNotTransientAnnotationPresent(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

}
