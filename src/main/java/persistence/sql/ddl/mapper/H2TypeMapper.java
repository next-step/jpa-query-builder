package persistence.sql.ddl.mapper;

public class H2TypeMapper implements TypeMapper {

    private static final String EMPTY_STRING = "";

    @Override
    public String getTypeString(Class<?> type, int length) {
        H2DataType dataType = H2DataType.of(type);
        return String.join(EMPTY_STRING,
                dataType.name(),
                getTypeLength(dataType, length)
        );
    }

    private String getTypeLength(H2DataType dataType, int length) {
        if (dataType.getDefaultLength() == null) {
            return EMPTY_STRING;
        }
        if (length == 0) {
            return "(" + dataType.getDefaultLength() + ")";
        }
        return "(" + length + ")";
    }
}
