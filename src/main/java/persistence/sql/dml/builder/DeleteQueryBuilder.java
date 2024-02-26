package persistence.sql.dml.builder;

import persistence.sql.dml.clause.Delete;
import persistence.sql.dml.clause.ValuesClause;

public class DeleteQueryBuilder {
    public String generateSQL(Object object) throws IllegalAccessException {
        Delete delete = new Delete(object.getClass());
        return String.format("delete %s where %s = %s",
                delete.getTableName(),
                delete.getPKName(),
                new ValuesClause(object).getPkValue()
        );
    }
}
