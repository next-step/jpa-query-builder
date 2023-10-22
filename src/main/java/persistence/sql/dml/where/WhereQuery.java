package persistence.sql.dml.where;

public class WhereQuery {

    private final Class<?> entityType;

    private WhereClauseType whereClauseType;

    private final String targetColumn;

    private final Object targetValue;

    private WhereQuery(Class<?> entityType, WhereClauseType whereClauseType, String targetColumn, Object targetValue) {
        this.entityType = entityType;
        this.whereClauseType = whereClauseType;
        this.targetColumn = targetColumn;
        this.targetValue = targetValue;
    }

    public String getTargetColumn() {
        return targetColumn;
    }

    public Object getTargetValue() {
        return targetValue;
    }

    public WhereClauseType getWhereClauseType() {
        return whereClauseType;
    }

    public static Builder builder(Class<?> entityClass) {
        return new Builder(entityClass);
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public static class Builder {
        private final Class<?> entityClass;

        private WhereClauseType whereClauseType;

        private String targetColumn;

        private Object targetValue;

        public Builder(Class<?> entityClass) {
            if (entityClass == null) {
                throw new IllegalArgumentException("Entity class cannot be null");
            }

            this.entityClass = entityClass;
        }

        public Builder equal(String fieldName, Object value) {
            this.whereClauseType = WhereClauseType.EQUALS;
            this.targetColumn = fieldName;
            this.targetValue = value;
            return this;
        }

        public WhereQuery build() {
            return new WhereQuery(entityClass, whereClauseType, targetColumn, targetValue);
        }
    }
}
