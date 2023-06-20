package persistence.sql.ddl.column.option;

import jakarta.persistence.GeneratedValue;

public interface GeneratedValueStrategy {
    String generate(GeneratedValue generatedValue);
}
