package builder.ddl.h2.builder;

import builder.ddl.DDLColumnData;
import builder.ddl.DDLQueryBuilder;

import java.util.List;

public class H2DropQueryBuilder implements DDLQueryBuilder {

    private final static String DROP_QUERY = "DROP TABLE {tableName};";
    private final static String TABLE_NAME = "{tableName}";

    @Override
    public String buildQuery(String tableName, List<DDLColumnData> columns) {
        return dropTableQuery(tableName);
    }

    //Drop 쿼리 생성
    public String dropTableQuery(String tableName) {
        return DROP_QUERY.replace(TABLE_NAME, tableName);
    }
}
