package persistence.dialect;

import java.sql.Types;

public abstract class Dialect {

    public Dialect() {
    }

    public int javaTypeToJdbcType(Class javaType) {
        return switch (javaType.getTypeName()) {
            case "String" -> Types.VARCHAR;
            case "BigDecimal" -> Types.NUMERIC;
            case "Long" -> Types.BIGINT;
            case "Integer" -> Types.INTEGER;
            default -> Types.CHAR;
        };
    }

    public String castType(int sqlType) {
        return switch (sqlType) {
            case Types.VARCHAR -> "varchar";
            case Types.NUMERIC -> "numeric";
            case Types.BIGINT -> "bigint";
            case Types.INTEGER -> "int";
            default -> "varchar";
        };
    }

}
