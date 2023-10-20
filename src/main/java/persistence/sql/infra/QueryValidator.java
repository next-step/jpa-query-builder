package persistence.sql.infra;

import persistence.sql.ddl.attribute.EntityAttribute;

public interface QueryValidator {

    void validate(EntityAttribute entityAttribute);

}
