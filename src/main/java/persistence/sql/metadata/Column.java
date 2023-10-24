package persistence.sql.metadata;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class Column {
    private final String name;

    private final String type;

    private final Constraint constraint;

    private final boolean isTransient;

    public Column(Field field) {
        this.name = findName(field);
        this.type = ColumnType.convertTypeClassToName(field.getType());//convertTypeToString(field.getType());
        this.constraint = new Constraint(field);
        this.isTransient = field.isAnnotationPresent(Transient.class);
    }

    public String getName() {
        return name;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public String buildColumnToCreate() {
        return new StringBuilder()
                .append(name + " " + type)
                .append(constraint.buildNullable())
                .append(GeneratedType.convertTypeClassToName(constraint.getGeneratedType()))
                .append(constraint.buildPrimaryKey())
                .toString();
    }

    public boolean checkPossibleToInsert() {
        return !isTransient && !constraint.isPrimaryKey();
    }

    public boolean isPrimaryKey() {
        return constraint.isPrimaryKey();
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
}
