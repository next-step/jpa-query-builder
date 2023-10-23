package persistence.sql.infra;

import persistence.entity.attribute.EntityAttribute;

public interface QueryValidator {

    void validate(EntityAttribute entityAttribute);

}
