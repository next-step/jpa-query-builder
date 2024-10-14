package persistence.sql.ddl.dialect;

import jakarta.persistence.GenerationType;

public interface Dialect {
    String getFieldDefinition(int type);

    String getGenerationDefinition(GenerationType generationType);
}
