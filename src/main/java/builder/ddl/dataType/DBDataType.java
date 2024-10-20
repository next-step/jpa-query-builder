package builder.ddl.dataType;

import java.util.Arrays;

public enum DBDataType {
    H2(DB.H2, new H2DataType());

    private final static String NOT_ALLOWED_DATABASE = "지원하지 않는 데이터베이스입니다.";
    private final DB db;
    private final DataType dataType;

    DBDataType(DB db, DataType dataType) {
        this.db = db;
        this.dataType = dataType;
    }

    public DB getDb() {
        return db;
    }

    public DataType getDataType() {
        return dataType;
    }

    // dataType으로 H2DataType을 찾고 반환하는 메소드
    public static String findDataType(DB db, Class<?> clazz) {
        DataType dataType = Arrays.stream(values())
                .filter(type -> type.getDb().equals(db))
                .map(DBDataType::getDataType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_ALLOWED_DATABASE));

        return dataType.findDataTypeByClass(clazz);
    }
}
