package persistence.sql.ddl.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.KeyType;

public interface Dialect {
    String mapDataType(Class<?> type);
    String mapGenerationType(GenerationType strategy);
    String mapKeyType(KeyType keyType);
}
