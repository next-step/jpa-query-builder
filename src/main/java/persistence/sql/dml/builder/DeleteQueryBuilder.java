package persistence.sql.dml.builder;

import persistence.sql.dml.clause.DMLMeta;
import persistence.sql.dml.clause.ValuesClause;

public class DeleteQueryBuilder {
    public String generateSQL(Object object) throws IllegalAccessException {
        DMLMeta table = new DMLMeta(object.getClass());
        return String.format("delete %s where %s = %s",
                table.getTableName(),
                table.getPKName(),
                new ValuesClause(object).getPkValue()
        );
    }
}
