package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class Column {
    private final String name;

    private final String type;

    private final Constraint constraint;

    private final boolean isTransient;

    public Column(Field field) {
        this.name = findName(field);
        this.type = convertTypeToString(field.getType());
        this.constraint = new Constraint(field);
        this.isTransient = field.isAnnotationPresent(Transient.class);
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

    public boolean isTransient() {
        return isTransient;
    }

    public String buildColumnToCreate() {
        return new StringBuilder()
                .append(name + " " + type)
                .append(constraint.buildNullable())
                .append(constraint.bulidGeneratedType())
                .append(constraint.buildPrimaryKey())
                .toString();
    }

    public boolean checkPossibleToAddValue() {
        return !isTransient && !constraint.isPrimaryKey();
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
