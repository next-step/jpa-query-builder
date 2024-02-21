package persistence.sql.ddl.mapping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class H2IdentityPrimaryKeyGenerationType implements PrimaryKeyGenerationType {

    private static final String NOTHING = "";
    private static final String H2_IDENTITY = " AUTO_INCREMENT";

    @Override
    public String value(Field field) {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);

        // TODO: GenerationType 구분
        if (generatedValue != null && generatedValue.strategy().equals(GenerationType.IDENTITY)) {
            return H2_IDENTITY;
        }
        return NOTHING;
    }
}
