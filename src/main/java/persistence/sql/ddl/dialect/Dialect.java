package persistence.sql.ddl.dialect;

import persistence.sql.ddl.metadata.ColumnOption;

public interface Dialect {
    String getSqlType(Class<?> columnType);

    String getClause(ColumnOption option);
}
