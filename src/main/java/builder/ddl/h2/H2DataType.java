package builder.ddl.h2;

import java.util.Arrays;

public enum H2DataType {

    STRING(String.class, "VARCHAR(255)"),
    INTEGER(Integer.class, "INTEGER"),
    LONG(Long.class, "BIGINT");

    private final Class<?> dataType;
    private final String h2DataType;

    private final static String NOT_ALLOWED_DATATYPE = "지원하지 않은 데이터타입입니다. DataType: ";

    H2DataType(Class<?> dataType, String h2DataType) {
        this.dataType = dataType;
        this.h2DataType = h2DataType;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public String getH2DataType() {
        return h2DataType;
    }

    // dataType으로 H2DataType을 찾고 반환하는 메소드
    public static String findH2DataTypeByDataType(Class<?> dataType) {
        return Arrays.stream(values())
                .filter(type -> type.getDataType().equals(dataType))
                .map(H2DataType::getH2DataType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_ALLOWED_DATATYPE + dataType));
    }
}
