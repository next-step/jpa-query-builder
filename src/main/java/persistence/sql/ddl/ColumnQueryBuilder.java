package persistence.sql.ddl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnQueryBuilder {
    private static ColumnQueryBuilder queryBuilderDDL = new ColumnQueryBuilder();
    private ColumnQueryBuilder() { }
    public static ColumnQueryBuilder getInstance() {
        return queryBuilderDDL;
    }
    public List<ColumnInfo> extract(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).map(ColumnInfo::extract)
                .filter(ColumnInfo::isNotTransient).collect(Collectors.toList());
    }

    public String extractPrimaryKeyQuery(Class<?> clazz) {
        List<ColumnInfo> columnInfos = extract(clazz);
        StringBuilder sb = new StringBuilder();
        List<ColumnInfo> primaryKey = columnInfos.stream().filter(ColumnInfo::isPrimaryKey).collect(Collectors.toList());
        if(primaryKey.isEmpty()) {
            throw new IllegalArgumentException("Entity에 Id로 정의된 column이 존재하지 않습니다.");
        }
        sb.append("primary key (");
        sb.append(primaryKey.stream().map(ColumnInfo::getName).collect(Collectors.joining(", ")));
        sb.append(")");
        return sb.toString();
    }

    public String generateDdlQuery(Class<?> clazz) {
        List<ColumnInfo> columnInfos = extract(clazz);

        String columnQuery = columnInfos.stream().map(ColumnInfo::generateColumnDdlQuery).collect(Collectors.joining(", "));
        String primaryQuery = extractPrimaryKeyQuery(clazz);

        return String.join(", ", columnQuery, primaryQuery);
    }
}
