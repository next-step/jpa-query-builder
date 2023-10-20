package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralColumnInfo {

    private final String name;
    private final String dataType;
    private final List<ColumnMetaInfo> columnMetaInfos;

    public GeneralColumnInfo(Field field) {
        name = getFieldName(field);
        dataType = map(field.getType());
        columnMetaInfos = ColumnMetaInfoFactory.createColumnMetaInfo(field);
    }

    public String getDefinition() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" ");
        sb.append(dataType);
        sb.append(" ");

        if (columnMetaInfos != null && !columnMetaInfos.isEmpty()) {
            sb.append(getColumnMetaInfosValue());
        }

        if (sb.lastIndexOf(" ") == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    private String getColumnMetaInfosValue() {
        return columnMetaInfos.stream()
                .filter(ColumnMetaInfo::isValuePresent)
                .sorted(ColumnMetaInfo::compareTo)
                .map(ColumnMetaInfo::getValue)
                .collect(Collectors.joining(" "));
    }

    public String getName() {
        return name;
    }

    String map(Class<?> type) {
        // TODO 리팩토링
        if (type == String.class) {
            return "VARCHAR(255)";
        } else if (type == int.class || type == Integer.class) {
            return "INT";
        } else if (type == long.class || type == Long.class) {
            return "BIGINT";
        }

        throw new IllegalArgumentException("지원하지 않는 타입입니다.");
    }

    private String getFieldName(Field field) {
        if (field.isAnnotationPresent(Column.class)
                && !isAnnotationNameEmpty(field)) {
            Column column = field.getAnnotation(Column.class);
            return column.name();
        }

        return field.getName();
    }

    private boolean isAnnotationNameEmpty(Field field) {
        return field.getAnnotation(Column.class).name().equals("");
    }

}
