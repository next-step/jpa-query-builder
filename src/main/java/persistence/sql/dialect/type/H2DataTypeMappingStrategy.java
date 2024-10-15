package persistence.sql.dialect.type;

import java.util.List;

import static java.sql.Types.*;

public class H2DataTypeMappingStrategy implements DataTypeMappingStrategy {
    @Override
    public List<Integer> getMappingSqlCodes() {
        return List.of(BIGINT, INTEGER, VARCHAR);
    }

    @Override
    public Class<?> mapSqlCodeToJavaType(int typeCode) {
        return switch (typeCode) {
            case BIGINT -> Long.class;
            case INTEGER -> Integer.class;
            case VARCHAR -> String.class;
            default -> throw new IllegalArgumentException("UNKNOWN TYPE. sql code = " + typeCode);
        };
    }

    @Override
    public String mapSqlCodeToNamePattern(int typeCode) {
        return switch (typeCode) {
            case BIGINT -> "bigint";
            case INTEGER -> "int";
            case VARCHAR -> "varchar(%d)";
            default -> throw new IllegalArgumentException("UNKNOWN TYPE. sql code = " + typeCode);
        };
    }
}
