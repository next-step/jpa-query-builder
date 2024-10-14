package persistence.sql.ddl.create.component.constraint;

import jakarta.persistence.Id;
import persistence.sql.ddl.create.component.ComponentBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ConstraintComponentBuilder implements ComponentBuilder {
    private static final String INDENT = "\t";
    private final StringBuilder componentBuilder = new StringBuilder(INDENT);

    private ConstraintComponentBuilder(Field field, Annotation annotation) {
        this.componentBuilder
                .append("CONSTRAINT ");

        if (annotation.annotationType().equals(Id.class)) {
            appendPrimaryKeyConstraint(field.getName());
        } else {
            throw new IllegalArgumentException("Constraint type not supported!");
        }
    }

    public static ConstraintComponentBuilder of(Field field, Annotation annotation) {
        return new ConstraintComponentBuilder(field, annotation);
    }

    private void appendPrimaryKeyConstraint(String fieldName) {
        this.componentBuilder
                .append("pk_").append(fieldName).append(INDENT)
                .append("primary key (").append(fieldName).append(")").append(INDENT);
    }

    /* TODO : foreign key constraint, etc. */

    public StringBuilder getComponentBuilder() {
        return this.componentBuilder;
    }
}
