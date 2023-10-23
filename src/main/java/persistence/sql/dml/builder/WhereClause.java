package persistence.sql.dml.builder;

public class WhereClause {
    private final StringBuilder conditions = new StringBuilder();

    private void and(String condition) {
        if (conditions.length() > 0) {
            conditions.append(" AND ");
        }
        conditions.append(condition);
    }

    public void and(String fieldName, String value) {
        and(String.format("%s = '%s'", fieldName, value));
    }

    @Override
    public String toString() {
        if (conditions.length() == 0) {
            return "";
        }
        return " WHERE " + conditions;
    }
}
