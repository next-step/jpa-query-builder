package persistence.sql.ddl.dialect;

import jakarta.persistence.GenerationType;

public interface Dialect {
    String mapDataType(Class<?> type);
    String mapGenerationType(GenerationType strategy);
}
