package persistence.sql.dml;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DmlColumns {
    private final List<DmlColumn> columns;

    private DmlColumns(List<DmlColumn> columns) {
        this.columns = columns;
    }

    public static DmlColumns from(Object instance) {
        return Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> DmlColumn.from(instance, field))
                .collect(Collectors.collectingAndThen(Collectors.toList(), DmlColumns::new));
    }

    public List<String> getInsertColumnNames() {
        return columns.stream()
                .filter(DmlColumn::notGeneratedValue)
                .map(DmlColumn::getName)
                .toList();
    }

    public List<String> getInsertColumnValues() {
        return columns.stream()
                .filter(DmlColumn::notGeneratedValue)
                .map(DmlColumn::getStringValue)
                .toList();
    }
}
