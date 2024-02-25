package persistence.sql.dialect;

import persistence.sql.dialect.database.ConstraintsMapper;
import persistence.sql.dialect.database.TypeMapper;

public interface Dialect {

    TypeMapper getTypeMapper();

    ConstraintsMapper getConstantTypeMapper();
}
