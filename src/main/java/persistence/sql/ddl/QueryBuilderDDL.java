package persistence.sql.ddl;

import persistence.sql.meta.ColumnFields;
import persistence.sql.meta.TableInfo;

public class QueryBuilderDDL {
    private static QueryBuilderDDL queryBuilderDDL = new QueryBuilderDDL();
    private QueryBuilderDDL() { }
    public static QueryBuilderDDL getInstance() {
        return queryBuilderDDL;
    }

    public String buildCreateDdl(Class<?> clazz){
        String createFormat = "create table %s (%s);";
        TableInfo tableInfo = new TableInfo(clazz);
        ColumnFields columnInfos = new ColumnFields(clazz);
        return String.format(createFormat, tableInfo.getTableName(), columnInfos.generateDdlQuery());
    }

    public String buildDropDdl(Class<?> clazz) {
        String dropFormat = "drop table if exists %s;";
        TableInfo tableInfo = new TableInfo(clazz);
        return String.format(dropFormat, tableInfo.getTableName());
    }
}
