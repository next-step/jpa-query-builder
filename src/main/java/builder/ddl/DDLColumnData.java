package builder.ddl;

import builder.ddl.dataType.DB;
import builder.ddl.dataType.DBDataType;
import builder.ddl.dataType.DataType;

public record DDLColumnData(
        String columnName,
        String columnDataType,
        boolean isPrimaryKey,
        boolean isNotNull,
        boolean isAutoIncrement
) {

    // PK 컬럼을 생성한다.
    public static DDLColumnData createPk(String columnName, Class<?> columnDataType, boolean isAutoIncrement, DB db) {
        return new DDLColumnData(
                columnName,
                DBDataType.findDataType(db, columnDataType),
                true,
                true,
                isAutoIncrement
        );
    }

    //일반 컬럼을 생성한다.
    public static DDLColumnData createColumn(String columnName, Class<?> columnDataType, boolean isNotNull, DB db) {
        return new DDLColumnData(
                columnName,
                DBDataType.findDataType(db, columnDataType),
                false,
                isNotNull,
                false
        );
    }
}
