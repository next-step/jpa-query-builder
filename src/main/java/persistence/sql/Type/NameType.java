package persistence.sql.Type;

public class NameType {
    private String value;

    public NameType(String value) {
        this.value = value;
    }

    public void setName(String columnName) {
        if (columnName.isBlank() || columnName.isEmpty()) {
            return;
        }
        this.value = columnName;
    }

    public String getValue() {
        return value;
    }
}
