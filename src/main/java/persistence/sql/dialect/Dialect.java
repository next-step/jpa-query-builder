package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import persistence.exception.UnknownException;

import java.sql.Types;
import persistence.sql.ddl.type.ColumnType;

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

    default String getColumnValueFormat(Class<?> type, Object columnValue) {
        if (ColumnType.isVarcharType(type)) {
            return "'" + columnValue + "'";
        }
        return columnValue.toString();
    }

    default String getIdentifierGenerationType(GenerationType type) {
        if (GenerationType.IDENTITY == type) {
            return "auto_increment";
        }

        throw new UnknownException("generation type : " + type.name());
    }

}
