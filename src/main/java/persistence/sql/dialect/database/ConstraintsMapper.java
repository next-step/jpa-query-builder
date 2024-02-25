package persistence.sql.dialect.database;

import persistence.sql.entity.model.Constraints;

public interface ConstraintsMapper {

    String getConstantType(Constraints constantType);

}
