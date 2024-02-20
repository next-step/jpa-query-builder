package persistence.sql.dialect;

import persistence.sql.column.ColumnType;
import persistence.sql.column.MysqlColumnType;

public class MysqlDialect implements Dialect {

    @Override
    public ColumnType getColumn(Class<?> javaType) {
        return MysqlColumnType.convertToSqlColumnType(javaType);
    }
}
