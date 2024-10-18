package orm.dsl.sql_dialect.h2;

import orm.ColumnMeta;
import orm.SQLDialect;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class H2ColumnTypeMapper implements ColumnTypeMapper {

    @Override
    public SQLDialect getDialect() {
        return SQLDialect.H2;
    }

    public <T> String javaTypeToRDBType(Class<T> fieldType, ColumnMeta columnMeta) {

        if (fieldType.equals(String.class)) {
            return "VARCHAR(%d)".formatted(columnMeta.length());
        }

        if (fieldType.equals(Integer.class)) {
            return "INTEGER";
        }

        if (fieldType.equals(Long.class)) {
            return "BIGINT";
        }

        if (fieldType.equals(Boolean.class)) {
            return "BOOLEAN";
        }

        if (fieldType.equals(LocalDateTime.class)) {
            return "TIMESTAMP";
        }

        if (fieldType.equals(BigDecimal.class)) {
            return "DECIMAL";
        }

        throw new IllegalArgumentException("Unsupported type: " + fieldType);
    }
}
