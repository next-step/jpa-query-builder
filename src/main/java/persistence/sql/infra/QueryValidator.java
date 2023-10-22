package persistence.sql.infra;

import persistence.entitiy.attribute.EntityAttribute;

public interface QueryValidator {

    void validate(EntityAttribute entityAttribute);

}
