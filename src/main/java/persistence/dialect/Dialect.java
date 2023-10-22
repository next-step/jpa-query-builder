package persistence.dialect;

import java.sql.Types;

public abstract class Dialect {

    public Dialect() {
    }

    public String javaTypeToJdbcType(String javaType) {
        switch (javaType) {
            case "String":
                return castType(Types.VARCHAR);
            case "BigDecimal":
                return castType(Types.NUMERIC);
            case "Long":
                return castType(Types.BIGINT);
            case "Integer":
                return castType(Types.INTEGER);
        }
        return castType(Types.CHAR);
    }

    public String castType(int sqlType) {
        switch (sqlType) {
            case Types.VARCHAR:
                return "varchar";
            case Types.NUMERIC:
                return "numeric";
            case Types.BIGINT:
                return "bigint";
            case Types.INTEGER:
                return "int";

        }
        return "varchar";
    }

}
