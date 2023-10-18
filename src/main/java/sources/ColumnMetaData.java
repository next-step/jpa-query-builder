package sources;

public class ColumnMetaData {

    private String name;
    private String type;
    private boolean nullable;

    public ColumnMetaData(String name, String type, boolean nullable) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
    }

    public String queryBuilder() {
        if(nullable) {
            return name + " " + type;
        }
        return name + " " + type + " is not null";
    }
}
