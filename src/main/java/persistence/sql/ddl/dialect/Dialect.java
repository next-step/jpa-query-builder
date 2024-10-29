package persistence.sql.ddl.dialect;

import persistence.sql.metadata.ColumnOption;

public interface Dialect {
    String getSqlType(Class<?> columnType);

    String getClause(ColumnOption option);
}
