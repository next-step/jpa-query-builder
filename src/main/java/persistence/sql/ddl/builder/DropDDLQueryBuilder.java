package persistence.sql.ddl.builder;

import persistence.entity.attribute.EntityAttribute;
import persistence.sql.ddl.converter.SqlConverter;
import persistence.sql.ddl.wrapper.DDLWrapper;
import persistence.sql.ddl.wrapper.DropDDLWrapper;


public class DropDDLQueryBuilder implements DDLQueryBuilder {

    public DropDDLQueryBuilder() {
    }

    @Override
    public String prepareStatement(EntityAttribute entityAttribute, SqlConverter sqlConverter) {
        DDLWrapper ddlWrapper = new DropDDLWrapper(sqlConverter);
        return entityAttribute.prepareDDL(ddlWrapper);
    }
}
