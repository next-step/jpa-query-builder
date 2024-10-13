package persistence.sql.ddl.dialect;

import persistence.sql.ddl.EntityField;

public interface Dialect {
    String getFieldDefinition(int type);

    String getIdFieldDefinition(int type);
}
