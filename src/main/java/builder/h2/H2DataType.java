package builder.h2;

import java.util.Arrays;

public enum H2DataType {

    STRING("java.lang.String", "VARCHAR(255)"),
    INTEGER("java.lang.Integer", "INTEGER"),
    LONG("java.lang.Long", "BIGINT");

    private final String dataType;
    private final String h2DataType;

    private final static String NOT_ALLOWED_DATATYPE = "지원하지 않은 데이터타입입니다. DataType: ";

    H2DataType(String dataType, String h2DataType) {
        this.dataType = dataType;
        this.h2DataType = h2DataType;
    }

    public String getDataType() {
        return dataType;
    }

    public String getH2DataType() {
        return h2DataType;
    }

    // dataType으로 H2DataType을 찾고 반환하는 메소드
    public static String findH2DataTypeByDataType(String dataType) {
        return Arrays.stream(values())
                .filter(type -> type.getDataType().equalsIgnoreCase(dataType))
                .map(H2DataType::getH2DataType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_ALLOWED_DATATYPE + dataType));
    }
}
