package persistence.sql.ddl.column.option;

import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColumnOptionStrategy implements OptionStrategy {
    private static final String NOT_NULL = "not null";

    @Override
    public Boolean supports(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    @Override
    public String options(Field field) {
        List<String> options = new ArrayList<>();
        Column column = field.getAnnotation(Column.class);

        if (!column.nullable()) {
            options.add(NOT_NULL);
        }

        return String.join(DELIMITER, options);
    }
}
