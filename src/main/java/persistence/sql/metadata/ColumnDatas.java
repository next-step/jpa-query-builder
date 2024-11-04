package persistence.sql.metadata;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnDatas {
    private final List<ColumnData> values;

    public ColumnDatas(List<ColumnData> values) {
        this.values = values;
    }

    public List<String> getColumnNames() {
        return values.stream()
                .map(ColumnData::getName)
                .toList();
    }

    public List<String> getColumnValues() {
        return values.stream()
                .map(ColumnData::getValue)
                .toList();
    }

    public List<ColumnData> getAll() {
        return Collections.unmodifiableList(values);
    }

    public ColumnData getPrimaryKey() {
        return values.stream()
                .filter(ColumnData::isPrimaryKey)
                .findFirst()
                .orElseThrow();
    }

    public ColumnDatas getInsertColumns() {
        return values.stream()
                .filter(ColumnData::hasNotIdentityStrategy)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ColumnDatas::new));
    }
}
