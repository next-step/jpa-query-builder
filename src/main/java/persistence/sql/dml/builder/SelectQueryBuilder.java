package persistence.sql.dml.builder;

import persistence.entity.attribute.EntityAttribute;

public class SelectQueryBuilder {
    private final String tableName;
    private final WhereClause whereClause = new WhereClause();

    private SelectQueryBuilder(EntityAttribute entityAttribute) {
        this.tableName = entityAttribute.getTableName();
    }

    public static SelectQueryBuilder of(EntityAttribute entityAttribute) {
        return new SelectQueryBuilder(entityAttribute);
    }

    public SelectQueryBuilder where(String fieldName, String value) {
        whereClause.and(fieldName, value);
        return this;
    }

    public String prepareStatement() {
        return String.format("SELECT * FROM %s%s", tableName, whereClause);
    }
}
