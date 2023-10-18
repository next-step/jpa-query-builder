package persistence.sql.ddl.schema.constraint;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.lang.reflect.Field;
import java.util.Objects;
import persistence.sql.ddl.ColumnAnnotationHelper;
import persistence.sql.ddl.dialect.ColumnType;
import persistence.sql.ddl.exception.UnrecognizedGeneratedValueException;

public class PrimaryKeyConstraint implements Constraint {

    private final String constraint;
    private static final String PRIMARY_KEY_FORMAT = "%s %s";
    private static final String PRIMARY_KEY = "PRIMARY KEY";

    public PrimaryKeyConstraint(Field field, ColumnType columnType) {
        this.constraint = extractGeneratedValueStrategy(field, columnType);
    }

    @Override
    public String getConstraint() {
        return constraint;
    }

    private String extractGeneratedValueStrategy(Field field, ColumnType columnType) {
        if (!ColumnAnnotationHelper.isPrimaryKey(field)) {
            return "";
        }

        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return PRIMARY_KEY;
        }

        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);

        if (Objects.requireNonNull(generatedValue.strategy()) == GenerationType.IDENTITY) {
            return String.format(PRIMARY_KEY_FORMAT, PRIMARY_KEY, columnType.generationIdentity());
        }

        throw new UnrecognizedGeneratedValueException("Unexpected value: " + generatedValue.strategy());
    }

}
