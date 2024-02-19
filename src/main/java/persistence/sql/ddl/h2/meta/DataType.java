package persistence.sql.ddl.h2.meta;

import java.lang.reflect.Field;

public enum DataType {
    String("varchar(255)"),
    Long("bigint"),
    Integer("int"),
    ;

    private final String sql;

    DataType(java.lang.String sql) {
        this.sql = sql;
    }

    public static String getSQL(Field field) {
        String dataType = field.getType().getSimpleName();
        if (dataType.equals(DataType.String.name())) {
            return DataType.String.sql;
        }
        if (dataType.equals(DataType.Long.name())) {
            return DataType.Long.sql;
        }
        if (dataType.equals(DataType.Integer.name())) {
            return DataType.Integer.sql;
        }
        throw new IllegalArgumentException("Not Supported DataType");
    }
}
