package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;

public interface GeneratedValueStrategy {
    String generate(GeneratedValue generatedValue);
}
