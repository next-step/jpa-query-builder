package persistence.sql.domain;

import static persistence.sql.CommonConstant.SPACE;

public class Where {

    private final String tableName;

    private final StringBuilder query;

    private Where(String tableName) {
        this.tableName = tableName;
        this.query = new StringBuilder();
    }

    public static Where from(String tableName) {
        return new Where(tableName);
    }

    public String getWhereClause() {
        if (query.length() == 0) {
            throw new IllegalStateException("sql is empty");
        }
        return query.toString();
    }

    public Where and(Condition condition) {
        addCondition(WhereOperator.AND, condition);
        return this;
    }

    public Where or(Condition condition) {
        addCondition(WhereOperator.OR, condition);
        return this;
    }

    private void addCondition(WhereOperator whereOperator, Condition condition) {
        if (query.length() == 0) {
            query.append(condition.getCondition());
            return;
        }
        query.append(SPACE)
                .append(whereOperator.getOperator())
                .append(SPACE)
                .append(condition.getCondition());
    }

    public String getTableName() {
        return tableName;
    }
}
