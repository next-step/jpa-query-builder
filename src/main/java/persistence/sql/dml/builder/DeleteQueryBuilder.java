package persistence.sql.dml.builder;

import persistence.entity.attribute.EntityAttribute;

public class DeleteQueryBuilder {
    public String prepareStatement(EntityAttribute entityAttribute, String id) {
        return String.format("DELETE FROM %s where id = %s", entityAttribute.getTableName(), id);
    }
}
