package persistence.sql.ddl.dialect.database;

import persistence.sql.ddl.query.model.Constraints;

public interface ConstraintsMapper {

    String getConstantType(Constraints constantType);

}
