package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class EntityField {
    private String name;

    private boolean nullable;

    private EntityField(String name, boolean nullable) {
        this.name = name;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public boolean isNullable() {
        return nullable;
    }

    public static EntityField of(Field field) {
        return new EntityField(
            getName(field),
            isNullable(field)
        );
    }

    private static String getName(Field field) {
        Column column = field.getAnnotation(Column.class);

        if (column == null) {
            return field.getName();
        }

        return column.name();
    }

    private static boolean isNullable(Field field) {
        Column column = field.getAnnotation(Column.class);

        if (column == null) {
            return true;
        }

        return column.nullable();
    }
}
