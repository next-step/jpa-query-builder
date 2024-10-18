package orm.dsl.sql_dialect.h2;

import orm.ColumnMeta;
import orm.SQLDialect;

public interface ColumnTypeMapper {

    SQLDialect getDialect();

    <T> String javaTypeToRDBType(Class<T> fieldType, ColumnMeta columnMeta);

    static ColumnTypeMapper of(SQLDialect dialect) {
        return switch (dialect) {
            case H2 -> new H2ColumnTypeMapper();
            default -> throw new IllegalArgumentException("Unsupported dialect: " + dialect);
        };
    }
}
