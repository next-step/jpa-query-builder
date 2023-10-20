package hibernate.ddl;

import hibernate.entity.EntityObject;

public class DeleteQueryBuilder {

    private static final String DELETE_QUERY = "delete from %s where %s = %s;";

    public DeleteQueryBuilder() {
    }

    public String generateQuery(final EntityObject entityObject) {
        return String.format(DELETE_QUERY, entityObject.getTableName(), entityObject.getEntityId().getFieldName(), entityObject.getEntityIdValue());
    }
}
