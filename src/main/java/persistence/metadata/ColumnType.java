package persistence.metadata;

import common.ErrorCode;

import java.sql.Types;
import java.util.Arrays;

public enum ColumnType {

    BIGINT(Long.class, Types.BIGINT),
    VARCHAR(String.class, Types.VARCHAR),
    INTEGER(Integer.class, Types.INTEGER)
    ;

    private final Class<?> javaType;
    private final int sqlType;

    ColumnType(Class<?> javaType, int sqlType) {
        this.javaType = javaType;
        this.sqlType = sqlType;
    }

    public static int getSqlType(Class<?> javaType) {
        return Arrays.stream(values())
                .filter(type -> type.javaType == javaType)
                .findFirst()
                .map(type -> type.sqlType)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_MATCH_TYPE.getErrorMsg()));
    }

    public static boolean isVarcharType(Class<?> javaType) {
        return javaType == String.class;
    }

    public static boolean isNotVarcharType(Class<?> javaType) {
        return !isVarcharType(javaType);
    }

    public static boolean isVarcharType(int sqlType) {
        return Types.VARCHAR == sqlType;
    }

}
