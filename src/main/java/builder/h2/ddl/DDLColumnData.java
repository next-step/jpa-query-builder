package builder.h2.ddl;

import builder.h2.H2DataType;

public record DDLColumnData(
        String columnName,
        String columnDataType,
        boolean isPrimaryKey,
        boolean isNotNull,
        boolean isAutoIncrement
) {

    //PK 컬럼을 생성한다.
    public static DDLColumnData createPk(String columnName, Class<?> columnDataType, boolean isAutoIncrement) {
        return new DDLColumnData(
                columnName,
                H2DataType.findH2DataTypeByDataType(columnDataType),
                true,
                true,
                isAutoIncrement
        );
    }

    //일반 컬럼을 생성한다.
    public static DDLColumnData createColumn(String columnName, Class<?> columnDataType, boolean isNotNull) {
        return new DDLColumnData(
                columnName,
                H2DataType.findH2DataTypeByDataType(columnDataType),
                false,
                isNotNull,
                false
        );
    }
}
