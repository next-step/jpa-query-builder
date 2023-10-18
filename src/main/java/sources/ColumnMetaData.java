package sources;

public class ColumnMetaData {

    private String name;
    private String type;
    private boolean nullable = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public ColumnMetaData() {

    }

    public ColumnMetaData(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public ColumnMetaData(String name, String type, boolean nullable) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
    }

}
