package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public String buildDropDdl(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("drop table ");
        sb.append(getTableName(clazz)).append(";");
        return sb.toString();
    }

    private String getTableName(Class<?> clazz) {
        final var className = clazz.getSimpleName().toLowerCase();
        final var tableAnotation = clazz.getAnnotation(Table.class);
        if(Objects.isNull(tableAnotation)) {
            return className;
        }
        if(tableAnotation.name().isBlank()) {
            return className;
        }
        return tableAnotation.name();
    }

    private String getColumnInfos(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        List<ColumnInfo> columns = Arrays.stream(clazz.getDeclaredFields()).map(ColumnInfo::extract)
                .filter(ColumnInfo::isNotTransient).collect(Collectors.toList());

        for (ColumnInfo column : columns) {
            sb.append(getColumnLine(column));
            sb.append(", ");
        }
        sb.append(getPrimaryKey(columns));
        return sb.toString();
    }

    private String getColumnLine(ColumnInfo column) {
        StringBuilder sb = new StringBuilder();
        sb.append(column.getName()).append(" ");
        sb.append(column.getColumnType().getQueryDefinition());
        if(!column.getOptions().isEmpty()) {
            sb.append(" ").append(column.getOptions().stream().collect(Collectors.joining(" ")));
        }

        return sb.toString();
    }

    private String getPrimaryKey(List<ColumnInfo> columns) {
        StringBuilder sb = new StringBuilder();
        List<ColumnInfo> primaryKey = columns.stream().filter(ColumnInfo::isPrimaryKey).collect(Collectors.toList());
        if(primaryKey.isEmpty()) {
            throw new IllegalArgumentException("Entity에 Id로 정의된 column이 존재하지 않습니다.");
        }
        sb.append("primary key (");
        for (ColumnInfo column : primaryKey) {
            sb.append(column.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }
}
