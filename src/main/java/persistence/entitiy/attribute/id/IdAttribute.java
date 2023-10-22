package persistence.entitiy.attribute.id;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.converter.SqlConverter;

public abstract class IdAttribute {

    abstract public String prepareDDL(SqlConverter sqlConverter);

    abstract public String getColumnName();

    abstract public String getFieldName();

    abstract public GenerationType getGenerationType();
}
