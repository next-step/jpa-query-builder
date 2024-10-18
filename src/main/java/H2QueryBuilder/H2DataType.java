package H2QueryBuilder;

import common.ErrorCode;

import java.util.Arrays;

public enum H2DataType {
    LONG(Long.class, "BIGINT"),
    STRING(String.class, "VARCHAR"),
    INTEGER(Integer.class, "INT");

    private final Class<?> javaType;
    private final String sqlType;

    H2DataType(Class<?> javaType, String sqlType) {
        this.javaType = javaType;
        this.sqlType  = sqlType;
    }

    public String getSqlType() {
        return sqlType;
    }

    public static String findH2DataTypeByDataType(Class<?> dataType) {
        return Arrays.stream(values())
                .filter(type -> type.javaType.equals(dataType))
                .map(H2DataType::getSqlType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_ALLOWED_DATATYPE.getErrorMsg(dataType.getTypeName())));
    }

}
