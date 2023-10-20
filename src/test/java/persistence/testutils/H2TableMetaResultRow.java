package persistence.testutils;

import java.util.Objects;

public class H2TableMetaResultRow {
    private final String tableName;
    private final String columnName;
    private final String dataType;

    public H2TableMetaResultRow(String tableName, String columnName, String dataType) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.dataType = dataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        H2TableMetaResultRow that = (H2TableMetaResultRow) o;

        if (!Objects.equals(tableName, that.tableName)) return false;
        if (!Objects.equals(columnName, that.columnName)) return false;
        return Objects.equals(dataType, that.dataType);
    }
}
