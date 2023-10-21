package persistence.sql.dml.builder;

import persistence.sql.dml.value.EntityValue;
import persistence.sql.dml.wrapper.InsertDMLWrapper;

public class InsertDMLQueryBuilder implements DMLQueryBuilder {
    @Override
    public String prepareStatement(EntityValue entityValue) {
        InsertDMLWrapper wrapper = new InsertDMLWrapper();
        return entityValue.prepareDML(wrapper);
    }
}
