package persistence.sql.column;

import java.lang.reflect.Field;
import java.util.Arrays;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.dialect.Dialect;

public class IdColumn implements Column {

    private static final String PK_FORMAT = "%s %s %s";
    private static final String PRIMARY_KEY = "primary key";

    private final GeneralColumn generalColumn;
    private final IdGeneratedStrategy idGeneratedStrategy;

    public IdColumn(Field[] fields, Dialect dialect) {
        Field idField = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[INFO] No @Id annotation"));

        validateGeneratedValue(idField);
        GeneralColumn generalColumn = new GeneralColumn(idField, dialect);
        GeneratedValue annotation = idField.getAnnotation(GeneratedValue.class);
        IdGeneratedStrategy idGeneratedStrategy = dialect.getIdGeneratedStrategy(annotation.strategy());
        this.generalColumn = generalColumn;
        this.idGeneratedStrategy = idGeneratedStrategy;
    }

    private void validateGeneratedValue(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            throw new IllegalArgumentException("[INFO] No @GeneratedValue annotation");
        }
    }

    public IdGeneratedStrategy getIdGeneratedStrategy() {
        return idGeneratedStrategy;
    }

    @Override
    public String getDefinition() {
        return String.format(PK_FORMAT, generalColumn.getDefinition(), idGeneratedStrategy.getValue(), PRIMARY_KEY);
    }

    @Override
    public String getName() {
        return generalColumn.getName();
    }

    @Override
    public String getFieldName() {
        return generalColumn.getFieldName();
    }
}
