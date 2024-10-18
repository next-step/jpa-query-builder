package builder.ddl.h2.builder;

import builder.ddl.DDLColumnData;
import builder.ddl.DDLQueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class H2CreateQueryBuilder implements DDLQueryBuilder {

    private final static String CREATE_QUERY = "CREATE TABLE {tableName} ({columnDefinitions});";
    private final static String PRIMARY_KEY = " PRIMARY KEY";
    private final static String NOT_NULL = " NOT NULL";
    private final static String AUTO_INCREMENT = " AUTO_INCREMENT";
    private final static String COMMA = ", ";
    private final static String BLANK = " ";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_DEFINITIONS = "{columnDefinitions}";

    @Override
    public String buildQuery(String tableName, List<DDLColumnData> columns) {
        return createTableQuery(tableName, columns);
    }

    //create 쿼리를 생성한다.
    public String createTableQuery(String tableName, List<DDLColumnData> columns) {
        // 테이블 열 정의 생성
        String columnDefinitions = columns.stream()
                .map(column -> {
                    String definition = column.columnName() + BLANK + column.columnDataType();
                    // primary key인 경우 "PRIMARY KEY" 추가
                    if (column.isNotNull()) definition += NOT_NULL; //false면 NOT_NULL 조건 추가
                    if (column.isAutoIncrement()) definition += AUTO_INCREMENT; //true면 AutoIncrement 추가
                    if (column.isPrimaryKey()) definition += PRIMARY_KEY; //PK면 PK조건 추가
                    return definition;
                })
                .collect(Collectors.joining(COMMA));

        // 최종 SQL 쿼리 생성
        return CREATE_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_DEFINITIONS, columnDefinitions);
    }
}
