package persistence.sql.ddl.builder;

import persistence.sql.ddl.attribute.EntityAttribute;


public class DropDDLQueryBuilder implements DDLQueryBuilder {

    public DropDDLQueryBuilder() {
    }

    @Override
    public String prepareStatement(EntityAttribute entityAttribute) {
        return String.format("DROP TABLE %s;", entityAttribute.getTableName());
    }
}
