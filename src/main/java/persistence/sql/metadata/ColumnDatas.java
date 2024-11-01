package persistence.sql.metadata;

import java.util.List;

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
}
