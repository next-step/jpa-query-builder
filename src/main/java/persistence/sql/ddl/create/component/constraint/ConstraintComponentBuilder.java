package persistence.sql.ddl.create.component.constraint;

import jakarta.persistence.Id;
import persistence.sql.ddl.create.component.ComponentUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConstraintComponentBuilder {
    private String name;
    private String type;
    private String columnName;
    private final List<String> options = new ArrayList<>();

    private static final String INDENT = "\t";

    private ConstraintComponentBuilder() {
    }

    public static List<ConstraintComponentBuilder> from(Field field) {
        List<ConstraintComponentBuilder> constraintComponentBuilders = new ArrayList<>();

        if (field.isAnnotationPresent(Id.class)) {
            constraintComponentBuilders.add(primaryKeyConstraintFrom(field));
        }
        /* TODO : else if () ... appendForeignKeyConstraint, etc. */

        return constraintComponentBuilders;
    }

    private static ConstraintComponentBuilder primaryKeyConstraintFrom(Field field) {
        ConstraintComponentBuilder constraintComponentBuilder = new ConstraintComponentBuilder();
        constraintComponentBuilder.setPrimaryKeyName(field);
        constraintComponentBuilder.setPrimaryKeyType();
        constraintComponentBuilder.setColumnName(field);
        return constraintComponentBuilder;
    }

    private void setPrimaryKeyName(Field field) {
        this.name = "{TABLE_NAME}_pk_" + ComponentUtils.getNameFromField(field);
    }

    private void setPrimaryKeyType() {
        this.type = "primary key";
    }

    private void setColumnName(Field field) {
        this.columnName = ComponentUtils.getNameFromField(field);
    }

    /* TODO : foreign key constraint, etc. */

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("constraint").append(INDENT)
                .append(this.name).append(INDENT)
                .append(this.type).append(INDENT)
                .append("(").append(this.columnName).append(")").append(INDENT);
        this.options.forEach(option -> stringBuilder.append(option).append(INDENT));

        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
