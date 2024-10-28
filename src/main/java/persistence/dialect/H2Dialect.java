package persistence.dialect;

import common.ErrorCode;
import jakarta.persistence.GenerationType;

import java.sql.Types;

public class H2Dialect implements Dialect{
    @Override
    public String getColumnType(int type) {
        return switch(type) {
            case Types.BIGINT -> "bigint";
            case Types.VARCHAR -> "varchar";
            case Types.INTEGER -> "integer";
            default -> throw new IllegalArgumentException(ErrorCode.NOT_ALLOWED_DATATYPE.getErrorMsg());
        };
    }

    @Override
    public String getIdentifierGenerationType(GenerationType type) {
        return switch (type) {
            case IDENTITY -> "auto_increment";
            default -> throw new IllegalArgumentException(ErrorCode.NOT_ALLOWED_DATATYPE.getErrorMsg());
        };
    }
}
