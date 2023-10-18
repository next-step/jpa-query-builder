package persistence.sql.ddl;

import java.lang.reflect.Field;

public class Column {
    private String name;

    private String type;

    private Constraint constraint;

    public Column(Field field) {
        this.name = findName(field);
        this.type = convertTypeToString(field.getType());
        this.constraint = new Constraint(field);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    private String findName(Field field) {
        if(!field.isAnnotationPresent(jakarta.persistence.Column.class)) {
            return field.getName();
        }

        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);

        if("".equals(column.name())) {
            return field.getName();
        }

        return column.name();
    }

    private String convertTypeToString(Class<?> type) {
        switch (type.getSimpleName()) {
            case "Long" :
                return "BIGINT";

            case "String" :
                return "VARCHAR(255)";

            case "Integer" :
                return "INTEGER";

            default:
                throw new IllegalArgumentException(type.getSimpleName() + " : 정의되지 않은 타입입니다.");
        }
    }
}
