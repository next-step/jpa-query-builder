package persistence.sql.meta;

import org.h2.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnFields {

    private List<ColumnField> columnFields;

    public ColumnFields(Class<?> clazz) {
        this.columnFields = extract(clazz);
    }

    private List<ColumnField> extract(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).map(ColumnField::new)
                .filter(ColumnField::isNotTransient).collect(Collectors.toList());
    }

    public String extractPrimaryKeyQuery() {
        StringBuilder sb = new StringBuilder();
        List<ColumnField> primaryKey = columnFields.stream().filter(ColumnField::isPrimaryKey).collect(Collectors.toList());
        if(primaryKey.isEmpty()) {
            throw new IllegalArgumentException("Entity에 Id로 정의된 column이 존재하지 않습니다.");
        }
        sb.append("primary key (");
        sb.append(primaryKey.stream().map(ColumnField::getName).collect(Collectors.joining(", ")));
        sb.append(")");
        return sb.toString();
    }

    public String generateDdlQuery() {
        String columnQuery = columnFields.stream().map(ColumnFields::generateColumnDdlQuery).collect(Collectors.joining(", "));
        String primaryQuery = extractPrimaryKeyQuery();

        return String.join(", ", columnQuery, primaryQuery);
    }

    private static String generateColumnDdlQuery(ColumnField columnInfo) {
        String columnQuery = String.join(" ", columnInfo.getName(), columnInfo.getColumnType().getQueryDefinition());
        String optionQuery = columnInfo.getOptions().stream().collect(Collectors.joining(" "));
        if(StringUtils.isNullOrEmpty(optionQuery)) {
            return columnQuery;
        }
        return String.join(" ", columnQuery, optionQuery);
    }

    public List<ColumnField> getColumnFields() {
        return columnFields;
    }
}
