package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.KeyType;
import persistence.sql.mapping.DataType;

public interface Dialect {
    String mapDataType(DataType type);
    String mapGenerationType(GenerationType strategy);
    String mapKeyType(KeyType keyType);
}
