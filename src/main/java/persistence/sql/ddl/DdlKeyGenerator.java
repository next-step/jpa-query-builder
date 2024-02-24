package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public class DdlKeyGenerator {
    public static String createPrimaryKeyGenerateDDL(final Dialect dialect, final Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return createGenerateTypeDDL(dialect, field);
        }

        return "";
    }

    private static String createGenerateTypeDDL(final Dialect dialect, final Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            return dialect.generatorCreateDdl(field.getAnnotation(GeneratedValue.class).strategy());
        }

        return " not null";
    }
}
