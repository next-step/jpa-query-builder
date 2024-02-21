package domain;

public enum DataType {

    INT("int", "INT"),
    INTEGER("Integer", "INT"),
    BIGINT("Long", "bigint"),
    VARCHAR("String", "VARCHAR");

    private final String javaDataType;
    private final String sqlDataType;

    DataType(String javaDataType, String sqlDataType) {
        this.javaDataType = javaDataType;
        this.sqlDataType = sqlDataType;
    }

    public String getJavaDataType() {
        return javaDataType;
    }

    public String getSqlDataType() {
        return sqlDataType;
    }

    public static DataType from(String javaDataType) {
        for (DataType dataType : DataType.values()) {
            if (dataType.getJavaDataType().equals(javaDataType)) {
                return dataType;
            }
        }
        throw new IllegalStateException("지원하는 데이터 타입이 아닙니다.");
    }

    public boolean isVarcharType() {
        return this.sqlDataType.equals("VARCHAR");
    }
}
