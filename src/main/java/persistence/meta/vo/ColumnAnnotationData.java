package persistence.meta.vo;

public class ColumnAnnotationData {
    private final String tableFieldName;
    private final boolean isNullable;

    public ColumnAnnotationData(String tableFieldName, boolean isNullable) {
        this.tableFieldName = tableFieldName;
        this.isNullable = isNullable;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public boolean isNullable() {
        return isNullable;
    }
}
