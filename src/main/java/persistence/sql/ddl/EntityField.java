package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class EntityField {
    private String name;

    private Class<?> type;

    private boolean nullable;

    private EntityField(String name, Class<?> type, boolean nullable) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
    }

    public static EntityField of(Field field) {
        return new EntityField(
                getName(field),
                field.getType(),
                isNullable(field)
        );
    }

    private static String getName(Field field) {
        Column column = field.getAnnotation(Column.class);

        if (column == null || column.name().equals("")) {
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

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }
}
