package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import java.sql.Types;
import persistence.exception.UnknownException;

public class H2Dialect implements Dialect {

    @Override
    public String getColumnType(int type) {
        return switch(type) {
            case Types.BIGINT -> "bigint";
            case Types.VARCHAR -> "varchar";
            case Types.INTEGER -> "integer";
            default -> throw new UnknownException("sql type : " + type);
        };
    }

    @Override
    public String getIdentifierGenerationType(GenerationType type) {
        return switch (type) {
            case IDENTITY -> "auto_increment";
            default -> throw new UnknownException("generation type : " + type.name());
        };
    }

}
