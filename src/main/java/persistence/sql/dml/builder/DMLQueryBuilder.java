package persistence.sql.dml.builder;

import persistence.sql.dml.value.EntityValue;

public interface DMLQueryBuilder {
    String prepareStatement(EntityValue entityValue);

}
