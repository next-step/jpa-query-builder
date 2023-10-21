package persistence.sql.ddl.builder;

import persistence.sql.ddl.attribute.EntityAttribute;
import persistence.sql.ddl.wrapper.CreateDDLWrapper;
import persistence.sql.ddl.wrapper.DDLWrapper;

public class CreateDDLQueryBuilder implements DDLQueryBuilder {
    public CreateDDLQueryBuilder() {
    }

    public String prepareStatement(EntityAttribute entityAttribute) {
        DDLWrapper ddlWrapper = new CreateDDLWrapper();
        return entityAttribute.prepareDDL(ddlWrapper);
    }
}
