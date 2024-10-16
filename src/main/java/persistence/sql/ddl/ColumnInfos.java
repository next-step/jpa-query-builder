package persistence.sql.ddl;

import org.h2.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnInfos {

    private List<ColumnInfo> columnInfos;

    public ColumnInfos(Class<?> clazz) {
        this.columnInfos = extract(clazz);
    }

    public List<ColumnInfo> extract(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).map(ColumnInfo::extract)
                .filter(ColumnInfo::isNotTransient).collect(Collectors.toList());
    }

    public String extractPrimaryKeyQuery() {
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

    public String generateDdlQuery() {
        String columnQuery = columnInfos.stream().map(ColumnInfos::generateColumnDdlQuery).collect(Collectors.joining(", "));
        String primaryQuery = extractPrimaryKeyQuery();

        return String.join(", ", columnQuery, primaryQuery);
    }

    private static String generateColumnDdlQuery(ColumnInfo columnInfo) {
        String columnQuery = String.join(" ", columnInfo.getName(), columnInfo.getColumnType().getQueryDefinition());
        String optionQuery = columnInfo.getOptions().stream().collect(Collectors.joining(" "));
        if(StringUtils.isNullOrEmpty(optionQuery)) {
            return columnQuery;
        }
        return String.join(" ", columnQuery, optionQuery);
    }
}
