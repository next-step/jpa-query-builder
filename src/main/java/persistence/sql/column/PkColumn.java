package persistence.sql.column;

import jakarta.persistence.GeneratedValue;
import persistence.sql.ddl.GenerateType;

import java.lang.reflect.Field;

public class PkColumn extends JpaColumn {

    private static final String PRIMARY_KEY = "primary key";

    private final GenerateType generateType;

    public PkColumn(Field field) {
        super(field.getName(), ColumnType.toDdl(field.getType()));
        validateGeneratedValue(field);
        GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
        this.generateType = GenerateType.from(annotation.strategy());
    }

    private void validateGeneratedValue(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            throw new IllegalArgumentException("[INFO] No @GeneratedValue annotation");
        }
    }

    @Override
    public String getDefinition() {
        return getName() + columnType.getColumnDefinition() + SPACE + generateType.getValue() +
                SPACE + PRIMARY_KEY;
    }

}
