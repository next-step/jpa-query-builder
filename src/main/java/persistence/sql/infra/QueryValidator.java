package persistence.sql.infra;

import persistence.sql.attribute.EntityAttribute;

public interface QueryValidator {

    void validate(EntityAttribute entityAttribute);

}
