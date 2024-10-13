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

        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(getTableName(clazz)).append(" (");
        sb.append(getColumnInfos(clazz));
        sb.append(");");
        return sb.toString();
    }

    private String getTableName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    private String getColumnInfos(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        List<Column> columns = Arrays.stream(clazz.getDeclaredFields()).map(Column::new).collect(Collectors.toList());

        for (Column column : columns) {
            sb.append(getColumnLine(column));
            sb.append(", ");
        }
        sb.append(getPrimaryKey(columns));
        return sb.toString();
    }

    private String getColumnLine(Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append(column.getName()).append(" ");
        sb.append(column.getColumnType().getQueryDefinition());
        if(column.isPrimary()) sb.append(" not null");
        return sb.toString();
    }

    private String getPrimaryKey(List<Column> columns) {
        StringBuilder sb = new StringBuilder();
        List<Column> primaryKey = columns.stream().filter(Column::isPrimary).collect(Collectors.toList());
        if(primaryKey.isEmpty()) throw new IllegalArgumentException("Entity에 Id로 정의된 column이 존재하지 않습니다.");
        sb.append("primary key (");
        for (Column column : primaryKey) {
            sb.append(column.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }
}
