package persistence.sql.ddl.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.exception.UnknownException;

import java.sql.Types;

public interface Dialect {

    default String getColumnType(int type) {
        if (Types.BIGINT == type) {
            return "bigint";
        }

        if (Types.VARCHAR == type) {
            return "varchar";
        }

        if (Types.INTEGER == type) {
            return "integer";
        }

        throw new UnknownException("sql type : " + type);
    }

    String getIdentityGenerationType(GenerationType type);

}
