package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.column.ColumnType;
import persistence.sql.ddl.IdGeneratedStrategy;

public interface Dialect {

    ColumnType getColumn(Class<?> type);

    IdGeneratedStrategy getIdGeneratedStrategy(GenerationType strategy);
}
