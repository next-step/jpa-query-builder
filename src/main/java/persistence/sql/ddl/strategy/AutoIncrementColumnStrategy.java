package persistence.sql.ddl.strategy;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class AutoIncrementColumnStrategy extends CommonColumnStrategy {
    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";

    @Override
    public boolean isRequired(Field field) {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        return generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;
    }

    @Override
    public String fetchQueryPart() {
        return SPACE + AUTO_INCREMENT;
    }
}
