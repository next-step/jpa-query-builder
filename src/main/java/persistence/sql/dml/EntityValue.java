package persistence.sql.dml;

public class EntityValue {
    private String value;
    private String insertClause;

    public EntityValue(final String value, final String insertClause) {
        this.value = value;
        this.insertClause = insertClause;
    }

    public String getValue() {
        return value;
    }

    public String getInsertClause() {
        return insertClause;
    }
}
