package persistence.sql.ddl;

import java.sql.JDBCType;
import persistence.exception.NotSupertypeException;
import persistence.exception.NumberRangeException;

public class ColumnType {
    private static final String VARCHAR_FORMAT = "%s(%d)";
    private static final String INTEGER = "%s";
    private static final String BIGINT = "%s";
    private static final Integer DEFAULT_VARCHAR_LENGTH = 255;
    private static final Integer VARCHAR_MIN_LENGTH = 1;
    private final JDBCType jdbcType;
    private final Integer length;


    private ColumnType(JDBCType jdbcType, Integer length) {
        if (length < VARCHAR_MIN_LENGTH) {
            throw new NumberRangeException("길이는 1보다 작을 수 없습니다.");
        }
        this.jdbcType = jdbcType;
        this.length = length;
    }

    private ColumnType(JDBCType jdbcType) {
        this.jdbcType = jdbcType;

        if (jdbcType == JDBCType.VARCHAR) {
            this.length = DEFAULT_VARCHAR_LENGTH;
            return;
        }
        length = null;
    }

    public static ColumnType createColumn(JDBCType jdbcType) {
        return new ColumnType(jdbcType);
    }

    public static ColumnType createVarchar(int length) {
        return new ColumnType(JDBCType.VARCHAR, length);
    }

    public static ColumnType createVarchar() {
        return createVarchar(DEFAULT_VARCHAR_LENGTH);
    }

    public String getColumType(Direct direct) {
        if (jdbcType == JDBCType.VARCHAR) {
            return String.format(VARCHAR_FORMAT, direct.getVarchar(), length);
        }
        if (jdbcType == JDBCType.INTEGER) {
            return String.format(INTEGER, direct.getInteger());
        }
        if (jdbcType == JDBCType.BIGINT) {
            return String.format(BIGINT, direct.getBigInt());
        }
        throw new NotSupertypeException();
    }

    public String getColumType() {
        return getColumType(new H2Direct());
    }

}
