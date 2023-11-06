package persistence.sql.dml;

import java.util.Objects;

public class ColumnClause {
    String columnName;

    public ColumnClause(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ColumnClause that = (ColumnClause) o;

        return Objects.equals(columnName, that.columnName);
    }

    @Override
    public int hashCode() {
        return columnName != null ? columnName.hashCode() : 0;
    }
}
