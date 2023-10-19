package persistence.sql.ddl.dialect;

import persistence.sql.ddl.entity.EntityData;

public interface Query {

    String generateQuery(EntityData entityData);

}
