package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.column.ColumnType;
import persistence.sql.column.MysqlColumnType;
import persistence.sql.ddl.IdGeneratedStrategy;
import persistence.sql.ddl.MysqlIdGeneratedStrategy;

public class MysqlDialect implements Dialect {

    @Override
    public ColumnType getColumn(Class<?> javaType) {
        return MysqlColumnType.convertToSqlColumnType(javaType);
    }

    @Override
    public IdGeneratedStrategy getIdGeneratedStrategy(GenerationType strategy) {
        return MysqlIdGeneratedStrategy.from(strategy);
    }
}
