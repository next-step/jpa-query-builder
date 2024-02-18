package persistence.sql.ddl.strategy;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class NullableFalseColumnStrategy extends CommonColumnStrategy {

    private static final String NOT_NULL = "NOT NULL";

    @Override
    public boolean isRequired(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && !column.nullable();
    }

    @Override
    public String fetchQueryPart() {
        return SPACE + NOT_NULL;
    }
}
