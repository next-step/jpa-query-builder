package persistence.sql.dialect;

import persistence.sql.column.ColumnType;

public interface Dialect {

    ColumnType getColumn(Class<?> type);
}
