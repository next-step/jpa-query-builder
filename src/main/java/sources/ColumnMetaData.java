package sources;

public class ColumnMetaData {

    private String name;
    private String type;
    private int length = 255;
    private boolean nullable = true;

    public ColumnMetaData() {
    }

    public ColumnMetaData(String name, String type, int length, boolean nullable) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public ColumnMetaData name(String name) {
        this.name = name;
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

    public ColumnMetaData build() {
        return new ColumnMetaData(name, type, length, nullable);
    }
}
