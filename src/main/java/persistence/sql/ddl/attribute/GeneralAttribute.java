package persistence.sql.ddl.attribute;

import persistence.sql.ddl.converter.SqlConverter;

abstract public class GeneralAttribute {
    protected SqlConverter sqlConverter;

    public GeneralAttribute(SqlConverter sqlConverter) {
        this.sqlConverter = sqlConverter;
    }

    abstract public String makeComponent();

}
