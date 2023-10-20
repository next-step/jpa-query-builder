package persistence.sql.ddl;

public class ColumnMetaInfo {

    private final String value;
    private final int priority;

    public ColumnMetaInfo(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    public String getValue() {
        return value;
    }

    public boolean isValuePresent() {
        return value != null;
    }

    public int compareTo(ColumnMetaInfo columnMetaInfo) {
        return Integer.compare(priority, columnMetaInfo.priority);
    }

}
