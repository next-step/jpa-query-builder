package persistence.sql.ddl;

import jakarta.persistence.Id;
import persistence.sql.TableSQLMapper;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldConstraintsGenerator implements FieldSQLGenerator {

    public String generate(Field field) {
        return Stream.of(
                getPKConstraint(field)
            )
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
    }

    String getPKConstraint(Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            return null;
        }

        return "PRIMARY KEY(" + TableSQLMapper.getColumnName(field) + ")";
    }
}
