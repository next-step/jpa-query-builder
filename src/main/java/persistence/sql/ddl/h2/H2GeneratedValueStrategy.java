package persistence.sql.ddl.h2;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.GeneratedValueStrategy;

public class H2GeneratedValueStrategy implements GeneratedValueStrategy {
    @Override
    public String generate(GeneratedValue generatedValue) {

        if (generatedValue != null && GenerationType.IDENTITY == generatedValue.strategy()) {
            return "auto_increment";
        }
        return "";
    }
}
