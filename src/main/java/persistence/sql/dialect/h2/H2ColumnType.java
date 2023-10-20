package persistence.sql.dialect.h2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Java Type과 H2 컬럼 타입을 매핑하는 Enum
 */
public enum H2ColumnType {
    LONG(Long.class, "bigint"),
    INTEGER(Integer.class, "integer"),
    STRING(String.class, "varchar"),
    ;

    public final Class<?> fieldType;
    public final String dbType;

    H2ColumnType(Class<?> fieldType, String dbType) {
        this.fieldType = fieldType;
        this.dbType = dbType;
    }

    static final Map<Class<?>, H2ColumnType> typeMap = Arrays.stream(H2ColumnType.values())
            .collect(Collectors.toMap(
                    columnTypeEnum -> columnTypeEnum.fieldType,
                    Function.identity()));

}
