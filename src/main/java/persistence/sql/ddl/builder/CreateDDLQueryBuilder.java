package persistence.sql.ddl.builder;

import persistence.entity.attribute.EntityAttribute;
import persistence.sql.ddl.converter.SqlConverter;
import persistence.sql.ddl.wrapper.CreateDDLWrapper;
import persistence.sql.ddl.wrapper.DDLWrapper;

public class CreateDDLQueryBuilder implements DDLQueryBuilder {
    public CreateDDLQueryBuilder() {
    }

    @Override
    public String prepareStatement(EntityAttribute entityAttribute, SqlConverter sqlConverter) {
        DDLWrapper ddlWrapper = new CreateDDLWrapper(sqlConverter);
        return entityAttribute.prepareDDL(ddlWrapper);
    }
}
