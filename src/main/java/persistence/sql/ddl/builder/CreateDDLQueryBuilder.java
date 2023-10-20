package persistence.sql.ddl.builder;

import persistence.sql.ddl.attribute.EntityAttribute;

public class CreateDDLQueryBuilder implements DDLQueryBuilder {
    public CreateDDLQueryBuilder() {
    }

    public String prepareStatement(EntityAttribute entityAttribute) {
        return String.format("CREATE TABLE %s ( %s );", entityAttribute.getTableName(), entityAttribute.getAttributeComponents());
    }
}
