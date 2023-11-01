package sources;

public class ColumnMetaData {

    private String name;
    private String fieldName;
    private String type;
    private int length = 255;
    private boolean nullable = true;
    private String value;

    public ColumnMetaData() {
    }

    public ColumnMetaData(String name, String fieldName, String type, int length, boolean nullable, String value) {
        this.name = name;
        this.fieldName = fieldName;
        this.type = type;
        this.length = length;
        this.nullable = nullable;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public String getValue() {
        return value;
    }

    public boolean isNullable() {
        return nullable;
    }

    public ColumnMetaData name(String name) {
        this.name = name;
        return this;
    }

    public ColumnMetaData fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public ColumnMetaData type(String type) {
        this.type = type;
        return this;
    }

    public ColumnMetaData length(int length) {
        this.length = length;
        return this;
    }

    public ColumnMetaData nullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public ColumnMetaData value(String value) {
        this.value = value;
        return this;
    }

    public ColumnMetaData build() {
        return new ColumnMetaData(name, fieldName, type, length, nullable, value);
    }
}
