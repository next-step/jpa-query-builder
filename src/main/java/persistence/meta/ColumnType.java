package persistence.meta;

import java.sql.JDBCType;
import java.util.Objects;

public class ColumnType {
    private final JDBCType jdbcType;

    private ColumnType(JDBCType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public static ColumnType createColumn(JDBCType jdbcType) {
        return new ColumnType(jdbcType);
    }

    public static ColumnType createVarchar() {
        return new ColumnType(JDBCType.VARCHAR);
    }

    public boolean isVarchar() {
        return jdbcType == JDBCType.VARCHAR;
    }

    public boolean isInteger() {
        return jdbcType == JDBCType.INTEGER;
    }

    public boolean isBigInt() {
        return jdbcType == JDBCType.BIGINT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColumnType)) {
            return false;
        }
        ColumnType that = (ColumnType) o;
        return jdbcType == that.jdbcType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jdbcType);
    }
}
