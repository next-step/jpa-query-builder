package persistence.sql.ddl.builder;

import persistence.sql.ddl.attribute.EntityAttribute;
import persistence.sql.ddl.wrapper.DDLWrapper;
import persistence.sql.ddl.wrapper.DropDDLWrapper;


public class DropDDLQueryBuilder implements DDLQueryBuilder {

    public DropDDLQueryBuilder() {
    }

    @Override
    public String prepareStatement(EntityAttribute entityAttribute) {
        DDLWrapper ddlWrapper = new DropDDLWrapper();
        return entityAttribute.prepareDDL(ddlWrapper);
    }
}
