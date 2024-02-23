package persistence.sql;

import persistence.sql.ddl.mapper.ConstraintMapper;
import persistence.sql.ddl.mapper.H2ConstraintMapper;
import persistence.sql.ddl.mapper.H2TypeMapper;
import persistence.sql.ddl.mapper.TypeMapper;

public interface QueryBuilder {

    TypeMapper TYPE_MAPPER = new H2TypeMapper();
    ConstraintMapper CONSTRAINT_MAPPER = new H2ConstraintMapper();

    String build();

}
