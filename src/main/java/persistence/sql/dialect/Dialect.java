package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.column.ColumnType;
import persistence.sql.column.IdGeneratedStrategy;

import java.lang.reflect.Field;

public interface Dialect {

    ColumnType getColumn(Class<?> type);

    IdGeneratedStrategy getIdGeneratedStrategy(GenerationType strategy);

    boolean isNotAutoIncrement(Field field);
}
