package persistence.sql.ddl;

import persistence.sql.meta.ColumnInfos;
import persistence.sql.meta.TableInfo;

public class QueryBuilderDDL {
    private static QueryBuilderDDL queryBuilderDDL = new QueryBuilderDDL();
    private QueryBuilderDDL() { }
    public static QueryBuilderDDL getInstance() {
        return queryBuilderDDL;
    }

    public String buildCreateDdl(Class<?> clazz){
        TableInfo tableInfo = new TableInfo(clazz);
        ColumnInfos columnInfos = new ColumnInfos(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(tableInfo.getTableName()).append(" (");
        sb.append(columnInfos.generateDdlQuery());
        sb.append(");");
        return sb.toString();
    }

    public String buildDropDdl(Class<?> clazz) {
        TableInfo tableInfo = new TableInfo(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append("drop table if exists ");
        sb.append(tableInfo.getTableName()).append(";");
        return sb.toString();
    }
}
