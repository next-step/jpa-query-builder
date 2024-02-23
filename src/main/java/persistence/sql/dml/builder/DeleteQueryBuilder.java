package persistence.sql.dml.builder;

import persistence.sql.dml.caluse.ColumnsClause;
import persistence.sql.dml.caluse.ValuesClause;

public class DeleteQueryBuilder {
    public String generateSQL(Object object) throws IllegalAccessException {
        return String.format("delete users where %s = %s",
                new ColumnsClause(object.getClass()).getPkName(),
                new ValuesClause(object).getPkValue()
        );
    }

}
