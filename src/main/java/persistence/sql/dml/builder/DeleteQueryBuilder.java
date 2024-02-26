package persistence.sql.dml.builder;

import persistence.sql.dml.clause.Delete;
import persistence.sql.dml.clause.ValuesClause;
import persistence.sql.dml.factory.DeleteFactory;

public class DeleteQueryBuilder {
    public String generateSQL(Object object) {
        Delete delete = DeleteFactory.getDelete(object.getClass());
        return String.format("delete %s where %s = %s",
                delete.getTableName(),
                delete.getPKName(),
                new ValuesClause(object).getPkValue()
        );
    }
}
