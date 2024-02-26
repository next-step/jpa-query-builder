package persistence.sql.type;

public class NameType {
    private final String fieldName;
    private final String columnName;

    public NameType(String fieldName, String columnName) {
        this.fieldName = fieldName;
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
