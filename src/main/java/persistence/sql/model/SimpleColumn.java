package persistence.sql.model;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SimpleColumn extends Column {
    public SimpleColumn(String name, Type type, Condition condition) {
        super(name, type, condition);
    }

    public SimpleColumn(String name, Type type) {
        super(name, type);
    }

    public static SimpleColumn from(Field field) {
        var columnAnnotation = getColumnAnnotation(field);
        if (columnAnnotation.isEmpty()) {
            return new SimpleColumn(field.getName(), Type.from(field.getType()));
        }

        var column = columnAnnotation.get();
        var name = column.name().isBlank() ? field.getName() : column.name();
        var type = Type.from(field.getType());
        var condition = Condition.from(column);

        return new SimpleColumn(name, type, condition);
    }
}
