package persistence.sql.ddl;

public class DataType {

    private static final String LONG_DATA_TYPE = "BIGINT";
    private static final String INT_DATA_TYPE = "INT";
    private static final String STRING_DATA_TYPE = "VARCHAR";


    public static String makeSQLStringDataType(Class<?> type) {
        if (type.equals(Long.class)) {
            return LONG_DATA_TYPE;
        }

        if (type.equals(Integer.class)) {
            return INT_DATA_TYPE;
        }

        if (type.equals(String.class)) {
            return STRING_DATA_TYPE;
        }

        throw new IllegalArgumentException("해당되는 타입이 존재하지 않습니다.");
    }
}
