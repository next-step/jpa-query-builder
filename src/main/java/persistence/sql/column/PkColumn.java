package persistence.sql.column;

import jakarta.persistence.GeneratedValue;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;


public class PkColumn implements Column {

    private static final String PK_FORMAT = "%s %s %s";
    private static final String PRIMARY_KEY = "primary key";

    private final GeneralColumn generalColumn;
    private final IdGeneratedStrategy idGeneratedStrategy;


    public static PkColumn of(GeneralColumn generalColumn, Field field, Dialect dialect) {
        validateGeneratedValue(field);
        GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
        IdGeneratedStrategy idGeneratedStrategy = dialect.getIdGeneratedStrategy(annotation.strategy());
        return new PkColumn(generalColumn, idGeneratedStrategy);
    }

    private PkColumn(GeneralColumn generalColumn, IdGeneratedStrategy idGeneratedStrategy) {
        this.generalColumn = generalColumn;
        this.idGeneratedStrategy = idGeneratedStrategy;
    }

    private static void validateGeneratedValue(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            throw new IllegalArgumentException("[INFO] No @GeneratedValue annotation");
        }
    }

    @Override
    public String getDefinition() {
        return String.format(PK_FORMAT, generalColumn.getDefinition(), idGeneratedStrategy.getValue(), PRIMARY_KEY);
    }

    @Override
    public boolean isPk() {
        return true;
    }

    @Override
    public String getColumnName() {
        return generalColumn.getColumnName();
    }

}
