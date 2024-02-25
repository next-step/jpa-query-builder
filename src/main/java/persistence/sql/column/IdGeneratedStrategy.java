package persistence.sql.column;

import jakarta.persistence.GenerationType;

public interface IdGeneratedStrategy {

    String getValue();

    boolean isAutoIncrement();

    GenerationType getGenerationType();
}
