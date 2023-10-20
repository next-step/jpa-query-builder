package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnInfoCollection {

    private final List<GeneralColumnInfo> generalColumnInfos;

    public ColumnInfoCollection(Class<?> type) {
        generalColumnInfos = Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(GeneralColumnInfo::new)
                .collect(Collectors.toList());
    }

    public String getDefinition() {
        return generalColumnInfos.stream()
                .map(GeneralColumnInfo::getDefinition)
                .collect(Collectors.joining(","));
    }

    List<GeneralColumnInfo> getColumnInfos() {
        return generalColumnInfos;
    }

}
