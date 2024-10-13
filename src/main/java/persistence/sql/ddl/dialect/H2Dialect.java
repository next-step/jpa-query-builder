package persistence.sql.ddl.dialect;

import persistence.sql.ddl.EntityField;
import persistence.sql.ddl.exception.NotSupportException;

import java.sql.Types;

public class H2Dialect implements Dialect{
    @Override
    public String getFieldDefinition(int type) {
        return switch (type) {
            case Types.VARCHAR -> "VARCHAR(%d)";
            case Types.BIGINT ->  "BIGINT";
            case Types.INTEGER ->  "INTEGER";
            default -> throw new NotSupportException();
        };
    }

    @Override
    public String getIdFieldDefinition(int type) {
        return switch (type) {
            case Types.BIGINT ->  "BIGINT";
            case Types.INTEGER ->  "INTEGER";
            default -> throw new NotSupportException();
        };
    }
}
