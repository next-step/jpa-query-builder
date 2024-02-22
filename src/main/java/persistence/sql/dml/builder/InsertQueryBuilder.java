package persistence.sql.dml.builder;

import persistence.sql.dml.caluse.ColumnsClause;
import persistence.sql.dml.caluse.ValuesClause;
import persistence.sql.meta.TableName;

public class InsertQueryBuilder {
    public String generateSQL(Object object) {
        return String.format("insert into %s (%s) values (%s)",
                new TableName(object.getClass()).getTableName(),
                new ColumnsClause(object.getClass()).getColumns(),
                new ValuesClause(object).getValues());
    }
}
