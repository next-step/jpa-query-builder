package persistence.sql.ddl.type;

import persistence.sql.ddl.exception.NotExistException;

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
                .orElseThrow(() -> new NotExistException("sql type mapped to " + javaType.getName()));
    }

    public static boolean isNotVarcharType(Class<?> javaType) {
        return Types.VARCHAR != getSqlType(javaType);
    }

    public static boolean isVarcharType(int sqlType) {
        return Types.VARCHAR == sqlType;
    }

}
