package persistence.sql.dml.builder;

import persistence.sql.dml.caluse.ColumnsClause;
import persistence.sql.dml.caluse.TableClause;
import persistence.sql.dml.caluse.ValuesClause;

public class DeleteQueryBuilder {
    public String generateSQL(Object object) throws IllegalAccessException {
        return String.format("delete %s where %s = %s",
                new TableClause(object.getClass()).getTableName(),
                new ColumnsClause(object.getClass()).getPkName(),
                new ValuesClause(object).getPkValue()
        );
    }
}
