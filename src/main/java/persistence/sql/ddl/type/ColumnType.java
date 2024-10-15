package persistence.sql.ddl.type;

import java.sql.Types;

public enum ColumnType {

    BIGINT(Long.class, Types.BIGINT),
    VARCHAR(String.class, Types.VARCHAR),
    INTEGER(Integer.class, Types.INTEGER)
    ;

    private Class<?> javaType;
    private int sqlType;

    ColumnType(Class<?> javaType, int sqlType) {
        this.javaType = javaType;
        this.sqlType = sqlType;
    }

}
