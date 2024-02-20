package persistence.sql.column;

import java.util.Arrays;

import static persistence.sql.column.MetaDataMapper.SPACE;


public enum MysqlColumnType implements ColumnType {
    BIGINT(Long.class, "bigint", ""),
    VARCHAR(String.class, "varchar", "255"),
    INTEGER(Integer.class, "integer", ""),
    ;

    private final Class<?> javaType;
    private final String sqlType;
    private final String defaultValue;

    MysqlColumnType(Class<?> javaType, String sqlType, String defaultValue) {
        this.javaType = javaType;
        this.sqlType = sqlType;
        this.defaultValue = defaultValue;
    }

    public static MysqlColumnType convertToSqlColumnType(Class<?> javaType) {
        return Arrays.stream(values())
                .filter(columnType -> columnType.javaType.equals(javaType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[INFO] No supported javaType"));
    }

    @Override
    public String getColumnDefinition() {
        StringBuilder sb = new StringBuilder();
        sb.append(SPACE).append(sqlType);
        if (!defaultValue.isBlank()) {
            sb.append("(").append(defaultValue).append(")");
        }
        return sb.toString();
    }

    public String getSqlType() {
        return sqlType;
    }
}
