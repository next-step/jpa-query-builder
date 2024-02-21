package persistence.sql.ddl.dialect;

import persistence.sql.ddl.dialect.database.ConstraintsMapper;
import persistence.sql.ddl.dialect.database.TypeMapper;

public interface Dialect {

    TypeMapper getTypeMapper();

    ConstraintsMapper getConstantTypeMapper();
}
