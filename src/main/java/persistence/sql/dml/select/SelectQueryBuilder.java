package persistence.sql.dml.select;

import persistence.sql.NameUtils;

public class SelectQueryBuilder {
    private String tableName;

    private SelectQueryBuilder() {
    }

    public static SelectQueryBuilder newInstance() {
        return new SelectQueryBuilder();
    }

    public SelectQueryBuilder entityClass(Class<?> entityClass) {
        this.tableName = NameUtils.getTableName(entityClass);
        return this;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder
                .append("select ")
                .append("* ")
                .append("from ")
                .append(tableName);

        return stringBuilder.append(";").toString();
    }
}
