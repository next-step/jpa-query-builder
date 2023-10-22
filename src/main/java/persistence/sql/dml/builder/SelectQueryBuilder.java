package persistence.sql.dml.builder;

public class SelectQueryBuilder {
    private final String tableName;
    private final WhereClause whereClause = new WhereClause();

    private SelectQueryBuilder(Class<?> clazz) {
        this.tableName = clazz.getSimpleName();
    }

    public static SelectQueryBuilder of(Class<?> clazz) {
        return new SelectQueryBuilder(clazz);
    }

    public SelectQueryBuilder where(String fieldName, String value) {
        whereClause.and(fieldName, value);
        return this;
    }

    public String prepareStatement() {
        return String.format("SELECT * FROM %s%s", tableName, whereClause);
    }
}
