package persistence.sql.ddl;

import java.lang.reflect.Field;

public class IdColumnConstraint {

    private static final String PRIMARY_KEY_CONSTRAINT = "PRIMARY KEY";
    private final Field field;
    private final GeneratedValue generatedValue = new GeneratedValue();

    public IdColumnConstraint(Field field) {
        this.field = field;
    }

    public String getConstraint() {
        StringBuilder columnConstraint = new StringBuilder(PRIMARY_KEY_CONSTRAINT);

        if (field.isAnnotationPresent(jakarta.persistence.GeneratedValue.class)) {
            String strategy = generatedValue.getStrategy(
                field
                    .getAnnotation(jakarta.persistence.GeneratedValue.class)
                    .strategy()
            );

            columnConstraint
                .append(" ")
                .append(strategy);
        }
        return columnConstraint.toString();
    }

}
