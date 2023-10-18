package persistence.sql.ddl.dialect.h2;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * Java Type과 H2 컬럼 타입을 매핑하는 Enum
 */
public enum H2ColumnType {
    LONG(Long.class, "bigint", null),
    INTEGER(Integer.class, "integer", null),
    STRING(String.class, "varchar", H2ColumnTypeProperties::getVarcharLength),

    ;

    public final Class<?> fieldType;
    public final String dbType;
    public final Function<Field, String> properties;

    H2ColumnType(Class<?> fieldType, String dbType, Function<Field, String> properties) {
        this.fieldType = fieldType;
        this.dbType = dbType;
        this.properties = properties;
    }
}
