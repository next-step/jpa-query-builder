package persistence.sql.ddl.attribute.id;

import persistence.sql.ddl.converter.SqlConverter;

public abstract class IdAttribute {
    protected SqlConverter sqlConverter;

    public IdAttribute(SqlConverter sqlConverter) {
        this.sqlConverter = sqlConverter;
    }

    abstract public String prepareDDL();

    abstract public String getColumName();

    abstract public String getFieldName();
}
