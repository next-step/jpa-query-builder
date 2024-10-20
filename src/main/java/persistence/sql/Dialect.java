package persistence.sql;

import jakarta.persistence.GenerationType;

public interface Dialect {
    String getGenerationTypeQuery(GenerationType generationType);
}
