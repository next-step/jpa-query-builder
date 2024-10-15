package persistence.sql.ddl;

import jakarta.persistence.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilderDDL {
    private static QueryBuilderDDL queryBuilderDDL = new QueryBuilderDDL();
    private QueryBuilderDDL() { }
    public static QueryBuilderDDL getInstance() {
        return queryBuilderDDL;
    }

    public String buildCreateDdl(Class<?> clazz){
        if(!clazz.isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException("Entity로 정의되어 있지 않은 class를 입력하였습니다.");

        TableInfo table = TableInfo.extract(clazz);

        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(table.getTableName()).append(" (");
        sb.append(getColumnInfos(clazz));
        sb.append(");");
        return sb.toString();
    }

    public String buildDropDdl(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        TableInfo table = TableInfo.extract(clazz);

        sb.append("drop table if exists ");
        sb.append(table.getTableName()).append(";");
        return sb.toString();
    }

    private String getColumnInfos(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        List<ColumnInfo> columns = Arrays.stream(clazz.getDeclaredFields()).map(ColumnInfo::extract)
                .filter(ColumnInfo::isNotTransient).collect(Collectors.toList());

        String columnQuery = columns.stream().map(ColumnInfo::generateQuery).collect(Collectors.joining(", "));
        sb.append(String.join(", ", columnQuery, generatePrimaryKeyQuery(columns)));
        return sb.toString();
    }

    private String generatePrimaryKeyQuery(List<ColumnInfo> columns) {
        StringBuilder sb = new StringBuilder();
        List<ColumnInfo> primaryKey = columns.stream().filter(ColumnInfo::isPrimaryKey).collect(Collectors.toList());
        if(primaryKey.isEmpty()) {
            throw new IllegalArgumentException("Entity에 Id로 정의된 column이 존재하지 않습니다.");
        }
        sb.append("primary key (");
        sb.append(primaryKey.stream().map(ColumnInfo::getName).collect(Collectors.joining(", ")));
        sb.append(")");
        return sb.toString();
    }
}
