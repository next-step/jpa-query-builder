package persistence.sql.ddl.create.component.constraint;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConstraintComponentBuilder {
    private static final String INDENT = "\t";
    private static final String COMMA_NEW_LINE = ",\n";
    private final StringBuilder componentBuilder = new StringBuilder();

    private ConstraintComponentBuilder() {
    }

    public static List<ConstraintComponentBuilder> from(Field field) {
        List<ConstraintComponentBuilder> constraintComponentBuilders = new ArrayList<>();

        if (field.isAnnotationPresent(Id.class)) {
            constraintComponentBuilders.add(getPrimaryKeyConstraintComponent(field.getName()));
        }
        /* TODO : else if () ... appendForeignKeyConstraint, etc. */

        return constraintComponentBuilders;
    }

    private static ConstraintComponentBuilder getPrimaryKeyConstraintComponent(String fieldName) {
        ConstraintComponentBuilder constraintComponentBuilder = new ConstraintComponentBuilder();
        return constraintComponentBuilder
                .appendCommonConstraintPrefix()
                .appendPrimaryKeyConstraint(fieldName);
    }

    private ConstraintComponentBuilder appendCommonConstraintPrefix() {
        this.componentBuilder
                .append(INDENT)
                .append("CONSTRAINT ");
        return this;
    }

    private ConstraintComponentBuilder appendPrimaryKeyConstraint(String fieldName) {
        this.componentBuilder
                .append("pk_").append(fieldName).append(INDENT)
                .append("primary key (").append(fieldName).append(")").append(COMMA_NEW_LINE);
        return this;
    }

    /* TODO : foreign key constraint, etc. */

    public StringBuilder getComponentBuilder() {
        return this.componentBuilder;
    }
}
