package persistence.sql.type;

public class NameType {
    private final String fieldName;
    private String columnName;

    public NameType(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getValue() {
        if (this.columnName == null || this.columnName.isBlank() || this.columnName.isEmpty()) {
            return this.fieldName;
        }
        return this.columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
