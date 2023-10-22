package hibernate.dml;

import hibernate.entity.EntityClass;

public class DeleteQueryBuilder {

    private static final String DELETE_QUERY = "delete from %s where %s = %s;";

    public DeleteQueryBuilder() {
    }

    public String generateQuery(final EntityClass<?> entityClass, final Object id) {
        return String.format(DELETE_QUERY, entityClass.tableName(), entityClass.getEntityId().getFieldName(), id);
    }
}
