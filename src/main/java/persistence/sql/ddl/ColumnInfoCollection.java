package persistence.sql.ddl;

import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnInfoCollection {

    private final List<ColumnInfo> columnInfos;

    public ColumnInfoCollection(Class<?> type) {
        columnInfos = Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnInfo::new)
                .collect(Collectors.toList());
    }

    public String getDefinition() {
        return columnInfos.stream()
                .map(ColumnInfo::getDefinition)
                .collect(Collectors.joining(","));
    }

    List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

}
