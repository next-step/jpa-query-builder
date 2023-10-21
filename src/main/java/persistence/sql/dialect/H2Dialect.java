package persistence.sql.dialect;

import persistence.sql.dml.insert.InsertQuery;
import persistence.sql.vo.TableName;

public class H2Dialect implements Dialect{

    @Override
    public String insertBuilder(Class<?> cls, InsertQuery insertQuery) {
        StringBuilder sb = new StringBuilder();
        TableName tableName = insertQuery.getTableName(cls);

        return sb.toString();
    }

}
